package com.pavdev.autocommit.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavdev.autocommit.data.ConnectionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

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