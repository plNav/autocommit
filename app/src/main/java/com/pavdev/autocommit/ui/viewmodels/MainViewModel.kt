package com.pavdev.autocommit.ui.viewmodels

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.data.dtos.GitHubUpdateRequest
import com.pavdev.autocommit.domain.GitHubApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData(ConnectionStatus.DISCONNECTED)
    private val _content = MutableLiveData<String?>(null)
    private val _error = MutableLiveData<String?>(null)
    private val _sha = MutableLiveData<String?>(null)
    val status: LiveData<ConnectionStatus> = _status
    val content: LiveData<String?> = _content
    val error: LiveData<String?> = _error
    val sha: LiveData<String?> = _sha

    private val defaultFile = "README.md"
    private val defaultCommit = "Update README.md"
    private val defaultLine = " \n > Line from pavdev autocommit"

    init {
        getReadmeContents()
    }

    private fun getReadmeContents() {
        _status.value = ConnectionStatus.CONNECTING
        viewModelScope.launch {
            try {
                val response = GitHubApi.retrofitService.getRepoContents(defaultFile)
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

    fun updateReadmeContents(
        updatedContent: String? = null,
        commitMessage: String = defaultCommit
    ) {
        _status.value = ConnectionStatus.CONNECTING
        viewModelScope.launch {
            try {
                val encodedContent: String = if (updatedContent != null) {
                    Base64.encodeToString(updatedContent.toByteArray(), Base64.NO_WRAP)
                } else {
                    Base64.encodeToString(
                        "${_content.value} $defaultLine".toByteArray(),
                        Base64.NO_WRAP
                    )
                }
                val updateRequest = GitHubUpdateRequest(
                    message = commitMessage,
                    content = encodedContent,
                    sha = sha.value!!,
                )
                val response =
                    GitHubApi.retrofitService.updateFileContents(defaultFile, updateRequest)
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
}