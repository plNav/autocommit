package com.pavdev.autocommit.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pavdev.autocommit.ui.composables.ActionButton
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val status by mainViewModel.status.observeAsState()
    val appBarColor by animateColorAsState(
        label = "StatusColorFade",
        targetValue = status?.color ?: Color.Gray,
        animationSpec = tween(durationMillis = 1000),
    )
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Crossfade(
                    label = "StatusCrossFade",
                    targetState = status, animationSpec = tween(1000),
                ) { animatedStatus ->
                    Text(text = animatedStatus.toString())
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = appBarColor
            )
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    name = "Test Connection",
                    onClick = mainViewModel::testConnection,
                )
                ActionButton(
                    name = "Test Connection",
                    onClick = mainViewModel::testConnection,
                )
            }
        }
    }
}