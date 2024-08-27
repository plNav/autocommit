package com.pavdev.autocommit.ui.screens

import ActionButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.ui.composables.CustomTopAppBar
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val status by mainViewModel.status.observeAsState()
    val sha by mainViewModel.sha.observeAsState()
    val content by mainViewModel.content.observeAsState()

    Scaffold(topBar = {
        CustomTopAppBar(
            status = status,
            sha = sha,
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content.orEmpty(),
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                )
                Spacer(modifier = Modifier.height(8.dp))
                ActionButton(
                    name = "FAST COMMIT",
                    isEnabled = status == ConnectionStatus.CONNECTED,
                    onClick = mainViewModel::updateReadmeContents
                )
            }
        }
    }
}