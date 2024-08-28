package com.pavdev.autocommit.ui.screens

import com.pavdev.autocommit.ui.composables.ActionButton
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.ui.composables.appbar.CustomTopAppBar
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val status by mainViewModel.status.observeAsState()
    val sha by mainViewModel.sha.observeAsState()
    val content by mainViewModel.content.observeAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                status = status,
                sha = sha,
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
            ) {
                ActionButton(
                    name = "FAST COMMIT",
                    isEnabled = status == ConnectionStatus.CONNECTED,
                    onClick = mainViewModel::updateReadmeContents
                )
            }

        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {

            Crossfade(
                label = "ContentCrossFade",
                targetState = content.orEmpty(),
                animationSpec = tween(1000)
            ) { animatedContent ->
                Text(
                    text = animatedContent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                )
            }

        }
    }
}