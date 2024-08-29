package com.pavdev.autocommit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pavdev.autocommit.data.Settings
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

@Composable
fun SettingsScreen(mainViewModel: MainViewModel, onNavigateBack: () -> Unit) {
    val currentSettings  : Settings = mainViewModel.settings.value!!

    var username by remember { mutableStateOf(currentSettings.username) }
    var repository by remember { mutableStateOf(currentSettings.repository) }
    var defaultCommit by remember { mutableStateOf(currentSettings.defaultCommit) }
    var defaultRepoFile by remember { mutableStateOf(currentSettings.defaultRepoFile) }
    var defaultAddedLine by remember { mutableStateOf(currentSettings.defaultAddedLine) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = errorMessage == "Invalid Username"
        )

        TextField(
            value = repository,
            onValueChange = { repository = it },
            label = { Text("Repository") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = errorMessage == "Invalid Repository"
        )

        TextField(
            value = defaultRepoFile,
            onValueChange = { defaultRepoFile = it },
            label = { Text("Default Repository File") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = errorMessage == "Default repo file cannot be empty"
        )

        TextField(
            value = defaultCommit,
            onValueChange = { defaultCommit = it },
            label = { Text("Default Commit Message") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = errorMessage == "Default commit cannot be empty"
        )

        TextField(
            value = defaultAddedLine,
            onValueChange = { defaultAddedLine = it },
            label = { Text("Default Added Line") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            isError = errorMessage == "Default Added Line cannot be empty"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val newSettings = Settings(
                    username = username,
                    repository = repository,
                    defaultCommit = defaultCommit,
                    defaultRepoFile = defaultRepoFile,
                    defaultAddedLine = defaultAddedLine
                )

                errorMessage = newSettings.validate()

                if (errorMessage == null) {
                    mainViewModel.saveSettings(newSettings)
                   // onNavigateBack() // Navigate back after saving
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Settings")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}