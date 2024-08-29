package com.pavdev.autocommit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavdev.autocommit.data.Settings
import com.pavdev.autocommit.ui.theme.AutocommitTheme
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(mainViewModel: MainViewModel, onNavigateBack: () -> Unit) {
    val currentSettings: Settings = mainViewModel.settings.value!!

    var username by remember { mutableStateOf(currentSettings.username) }
    var repository by remember { mutableStateOf(currentSettings.repository) }
    var defaultCommit by remember { mutableStateOf(currentSettings.defaultCommit) }
    var defaultRepoFile by remember { mutableStateOf(currentSettings.defaultRepoFile) }
    var defaultAddedLine by remember { mutableStateOf(currentSettings.defaultAddedLine) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var isDialogOpen by remember { mutableStateOf(false) }
    var token by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Edit Settings") }, navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.padding(20.dp)
            ) {

                Column(verticalArrangement = Arrangement.SpaceAround) {
                    Button(
                        onClick = {
                            isDialogOpen = true
                        },
                        shape = RoundedCornerShape(4.dp)

                    ) {
                        Text(
                            text = "UPDATE TOKEN",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Text(
                        text = "For security reasons the token cannot be displayed",
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(Modifier.padding(20.dp)) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = errorMessage == "Invalid Username"
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = repository,
                    onValueChange = { repository = it },
                    label = { Text("Repository") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = errorMessage == "Invalid Repository"
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = defaultRepoFile,
                    onValueChange = { defaultRepoFile = it },
                    label = { Text("Default Repository File") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = errorMessage == "Default repo file cannot be empty"
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = defaultCommit,
                    onValueChange = { defaultCommit = it },
                    label = { Text("Default Commit Message") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = errorMessage == "Default commit cannot be empty"
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = defaultAddedLine,
                    onValueChange = { defaultAddedLine = it },
                    label = { Text("Default Added Line") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    isError = errorMessage == "Default Added Line cannot be empty",
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = errorMessage ?: " ", color = MaterialTheme.colorScheme.error)

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(20.dp)) {
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
                            onNavigateBack()
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "SAVE SETTINGS",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        if (isDialogOpen) {
            AutocommitTheme {
                AlertDialog(
                    onDismissRequest = { isDialogOpen = false },
                    title = { Text(text = "Update Token") },
                    text = {
                        Column {
                            Text("This will erase if you have one saved")
                            Spacer(modifier = Modifier.height(16.dp))
                            TextField(
                                value = token,
                                label = { Text("New Token") },
                                onValueChange = { token = it },
                                isError = token.isEmpty(),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                            )
                            if (token.isEmpty()) {
                                Text(
                                    text = "Token cannot be empty",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (token.isNotEmpty()) {
                                    mainViewModel.saveToken(token)
                                    isDialogOpen = false
                                }
                            }
                        ) {
                            Text("Save")
                        }
                    },
                )
            }
        }
    }
}