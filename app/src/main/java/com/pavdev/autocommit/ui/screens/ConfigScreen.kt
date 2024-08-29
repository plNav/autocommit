package com.pavdev.autocommit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavdev.autocommit.ui.viewmodels.MainViewModel
import com.pavdev.autocommit.util.CryptoManager
import com.pavdev.autocommit.util.FileManager
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Composable
fun ConfigScreen(mainViewModel: MainViewModel, filesDir: File) {
    val cryptoManager = CryptoManager()
    var messageToEncrypt by remember {
        mutableStateOf("")
    }
    var messageToDecrypt by remember {
        mutableStateOf("")
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = messageToEncrypt,
                onValueChange = { messageToEncrypt = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Encrypt string") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = {
                    messageToDecrypt = cryptoManager.encrypt(
                        bytes = messageToEncrypt.encodeToByteArray(),
                        outputStream = FileManager.getFileOutputStream(filesDir),
                    ).decodeToString()
                }) {
                    Text(text = "Encrypt")
                }
                Button(onClick = {
                    messageToEncrypt = cryptoManager.decrypt(
                        inputStream = FileManager.getFileInputStream(filesDir)
                    ).decodeToString()
                }) {
                    Text(text = "Decrypt")
                }
            }
            Text(text = messageToDecrypt)
        }

    }
}
