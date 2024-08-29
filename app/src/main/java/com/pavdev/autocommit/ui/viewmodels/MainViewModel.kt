package com.pavdev.autocommit.ui.viewmodels

import android.app.Application
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pavdev.autocommit.data.Settings
import com.pavdev.autocommit.data.dtos.GitHubUpdateRequest
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.domain.GitHubApi
import com.pavdev.autocommit.util.CryptoManager
import com.pavdev.autocommit.util.DataStoreManager
import com.pavdev.autocommit.util.FileManager
import kotlinx.coroutines.launch

class MainViewModel(application : Application) : AndroidViewModel(application) {

    private val cryptoManager = CryptoManager()
    private val dataStoreManager = DataStoreManager(application)

    private val filesDir = application.filesDir
    private var token : String? = null

    private val _status = MutableLiveData(ConnectionStatus.DISCONNECTED)
    private val _settings = MutableLiveData<Settings?>(null)
    private val _content = MutableLiveData<String?>(null)
    private val _error = MutableLiveData<String?>(null)
    private val _sha = MutableLiveData<String?>(null)

    val status: LiveData<ConnectionStatus> = _status
    val settings : LiveData<Settings?> = _settings
    val content: LiveData<String?> = _content
    val error: LiveData<String?> = _error
    val sha: LiveData<String?> = _sha

    init {
        loadCredentials()
        getReadmeContents()
    }

    private fun loadCredentials() {
        viewModelScope.launch {
           _settings.postValue(dataStoreManager.getSettings())
            token = decryptMessage()
        }
    }

    private fun validateCredentials() : String? {
        if(_settings.value == null ){
            return "Settings Null"
        }
        if(token == null){
            return "Token Null"
        }
        return null
    }

    private fun getReadmeContents() {
        validateCredentials()

        _status.value = ConnectionStatus.CONNECTING
        viewModelScope.launch {
            try {
                val response = GitHubApi.retrofitService.getRepoContents(_settings.value!!.defaultRepoFile)
                if (!response.isSuccessful) {
                    _status.postValue(ConnectionStatus.FAILED)
                    _error.postValue("GetContentsResponseFailed \n $response")
                    return@launch
                }
                val responseBody = response.body()
                responseBody?.sha?.let {
                    _sha.value = responseBody.sha
                }
                responseBody?.content?.let {
                    _content.value = String(Base64.decode(it, Base64.DEFAULT))
                }
                _status.postValue(ConnectionStatus.CONNECTED)
            } catch (e: Exception) {
                _status.postValue(ConnectionStatus.FAILED)
                _error.postValue("GetContentsException \n ${e.message}")
            }
        }
    }

    fun saveCredentials(settings : Settings, token : String){
        // TODO ENCRYPT AND
    }

    fun updateReadmeContents(
        updatedContent: String? = null,
        commitMessage: String = _settings.value!!.defaultCommit
    ) {
        _status.value = ConnectionStatus.CONNECTING
        viewModelScope.launch {
            try {
                val encodedContent: String = if (updatedContent != null) {
                    Base64.encodeToString(updatedContent.toByteArray(), Base64.NO_WRAP)
                } else {
                    Base64.encodeToString(
                        "${_content.value} ${_settings.value!!.defaultAddedLine}".toByteArray(),
                        Base64.NO_WRAP
                    )
                }
                val updateRequest = GitHubUpdateRequest(
                    message = commitMessage,
                    content = encodedContent,
                    sha = sha.value!!,
                )
                val response =
                    GitHubApi.retrofitService.updateFileContents(
                        _settings.value!!.defaultRepoFile, updateRequest
                    )
                if (response.isSuccessful) {
                    getReadmeContents()
                } else {
                    _status.postValue(ConnectionStatus.FAILED)
                    _error.postValue("UpdateContentsResponseFailed \n $response")
                }
            } catch (e: Exception) {
                _status.postValue(ConnectionStatus.FAILED)
                _error.postValue("UpdateContentsException \n ${e.message}")
            }
        }
    }

    fun encryptMessage(message: String): String {
        return cryptoManager.encrypt(
            bytes = message.encodeToByteArray(),
            outputStream = FileManager.getFileOutputStream(filesDir),
        ).decodeToString()
    }

    fun decryptMessage(): String {
        return cryptoManager.decrypt(
            inputStream = FileManager.getFileInputStream(filesDir)
        ).decodeToString()
    }

}