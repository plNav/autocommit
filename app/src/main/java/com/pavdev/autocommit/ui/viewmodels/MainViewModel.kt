package com.pavdev.autocommit.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavdev.autocommit.data.ConnectionStatus
import com.pavdev.autocommit.domain.network.GitHubApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MainUiStatus {
    data class Success(val photos: String) : MainUiStatus
    data class Error(val error : Exception) : MainUiStatus
    data object Loading : MainUiStatus
}

class MainViewModel : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MainUiStatus by mutableStateOf(MainUiStatus.Loading)
        private set

    /**
     * Call getRepoData() on init so we can display status immediately.
     */
    init {
        getRepoData()
    }

    private fun getRepoData() {
        viewModelScope.launch {
            marsUiState = MainUiStatus.Loading
            marsUiState = try {
                val listResult = GitHubApi.retrofitService.getPhotos()
                MainUiStatus.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                MainUiStatus.Error(e)
            } catch (e: HttpException) {
                MainUiStatus.Error(e)
            }
        }
    }

    /** LiveData is another way to handle state */
    private val _status = MutableLiveData(ConnectionStatus.DISCONNECTED)
    val status: LiveData<ConnectionStatus> = _status;

    fun testConnection() {
        viewModelScope.launch {
            try {
                Log.i("_DEV_", "Testing connection...")
                _status.value = ConnectionStatus.CONNECTING
                delay(2000L)
                Log.i("_DEV_", "Connection successful")
                _status.postValue(ConnectionStatus.CONNECTED)
            } catch (e: Exception) {
                Log.e("_DEV_", "Connection failed: ${e.message}")
                _status.postValue(ConnectionStatus.FAILED)
            }
        }
    }

}