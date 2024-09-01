package com.pavdev.autocommit.ui.viewmodels

import android.app.Application
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pavdev.autocommit.data.dtos.GitHubUpdateRequest
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.data.models.Settings
import com.pavdev.autocommit.domain.GitHubApi
import com.pavdev.autocommit.util.CryptoManager
import com.pavdev.autocommit.util.DataStoreManager
import com.pavdev.autocommit.util.FileManager
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val cryptoManager = CryptoManager()
    private val dataStoreManager = DataStoreManager(application)
    private val githubApi = GitHubApi()

    private val filesDir = application.filesDir
    private var token: String? = null

    private val _status = MutableLiveData(ConnectionStatus.CONNECTING)
    private val _settings = MutableLiveData<Settings?>(null)
    private val _content = MutableLiveData<String?>(null)
    private val _error = MutableLiveData<String?>(null)
    private val _sha = MutableLiveData<String?>(null)

    val status: LiveData<ConnectionStatus> = _status
    val settings: LiveData<Settings?> = _settings
    val content: LiveData<String?> = _content
    val error: LiveData<String?> = _error
    val sha: LiveData<String?> = _sha

    init {
        loadCredentials()
    }

    private fun loadCredentials() {
        viewModelScope.launch {
            try {
                val storedSettings = dataStoreManager.getSettings()
                _settings.value = storedSettings
                token = decryptMessage()
                if (!validateCredentials()) {
                    _status.postValue(ConnectionStatus.DISCONNECTED)
                    return@launch
                }
                githubApi.configure(
                    token = token,
                    username = storedSettings.username,
                    repo = storedSettings.repository
                )
                getRepoContent()
            } catch (e: Exception) {
                _status.postValue(ConnectionStatus.DISCONNECTED)
                _error.postValue("Token Not Configured")
            }
        }
    }

    private fun validateCredentials(): Boolean {
        if (_settings.value == null) {
            _error.value = "Settings Null"
            return false
        }
        if (token == null) {
            _error.value = "Token Null"
            return false
        }
        val validation = _settings.value!!.validate()
        if (validation != null) {
            _error.value = validation
            return false
        }
        return true
    }

    private fun getRepoContent() {
        _status.value = ConnectionStatus.CONNECTING
        viewModelScope.launch {
            try {
                val response = githubApi.getRepoContents(_settings.value!!.defaultRepoFile)
                if (!response.isSuccessful) {
                    _status.postValue(ConnectionStatus.FAILED)
                    _error.postValue("GetContentsResponseFailed \n $response")
                    return@launch
                }
                val responseBody = response.body()
                responseBody?.sha?.let {
                    _sha.value = it
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

    fun saveToken(token: String) {
        this.token = token
        cryptoManager.encrypt(
            bytes = token.toByteArray(),
            outputStream = FileManager.getFileOutputStream(filesDir)
        )
        githubApi.configure(token = token)
        getRepoContent()
    }

    fun saveSettings(settings: Settings) {
        _settings.value = settings
        githubApi.configure(username = settings.username, repo = settings.repository)
        viewModelScope.launch { dataStoreManager.saveSettings(settings) }
        getRepoContent()
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
                    githubApi.updateFileContents(
                        _settings.value!!.defaultRepoFile, updateRequest
                    )
                if (response.isSuccessful) {
                    getRepoContent()
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

    private fun decryptMessage(): String {
        return cryptoManager.decrypt(
            inputStream = FileManager.getFileInputStream(filesDir)
        ).decodeToString()
    }

}