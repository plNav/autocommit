package com.pavdev.autocommit.ui.screens

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.ui.composables.ActionButton
import com.pavdev.autocommit.ui.composables.MainContent
import com.pavdev.autocommit.ui.composables.appbar.CustomTopAppBar
import com.pavdev.autocommit.ui.theme.AutocommitTheme
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel, onNavigateToConfig: () -> Unit) {
    val status by mainViewModel.status.observeAsState()
    val sha by mainViewModel.sha.observeAsState()
    val content by mainViewModel.content.observeAsState()
    val error by mainViewModel.error.observeAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                status = status,
                sha = sha,
                onNavigateSettings = onNavigateToConfig
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
            ) {
                ActionButton(
                    name = "AUTOCOMMIT",
                    isEnabled = status == ConnectionStatus.CONNECTED,
                    onClick = mainViewModel::updateReadmeContents
                )
            }
        },
    ) { innerPadding ->
        MainContent(
            innerPadding = innerPadding,
            content = content,
            status = status!!,
            error = error,
            onNavigateToConfig = onNavigateToConfig
        )
    }
}

@Suppress("KotlinConstantConditions")
@Preview
@Composable
fun MainScreenPreview() {
    val status = ConnectionStatus.DISCONNECTED
    val sha = "1234123412341234123412341234"
    val content = "Preview Content"
    val isDarkTheme = false
    val error = "Preview Error"
    AutocommitTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    status = status,
                    sha = sha,
                    onNavigateSettings = {}
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                ) {
                    ActionButton(
                        name = "AUTOCOMMIT",
                        isEnabled = status == ConnectionStatus.CONNECTED,
                        onClick = {}
                    )
                }
            },
        ) { innerPadding ->
            MainContent(
                innerPadding = innerPadding,
                content = content,
                status = status,
                error = error,
                onNavigateToConfig = {}
            )
        }
    }
}
