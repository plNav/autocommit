package com.pavdev.autocommit.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavdev.autocommit.data.enums.ConnectionStatus

@Composable
fun MainContent(
    innerPadding: PaddingValues,
    content: String?,
    status: ConnectionStatus,
    error: String?,
    onNavigateToConfig: () -> Unit
) {
    var textSize by remember { mutableStateOf(16.sp) }
    val minTextSize = 2.sp
    val maxTextSize = 60.sp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Crossfade(
            label = "Status Content Crossfade",
            targetState = status,
            animationSpec = tween(1000)
        ) { animatedStatus ->
            when (animatedStatus) {
                ConnectionStatus.CONNECTING -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                        )
                    }
                }

                ConnectionStatus.DISCONNECTED -> {
                    ContentError(error = error, onNavigateToConfig = onNavigateToConfig)
                }

                ConnectionStatus.FAILED -> {
                    ContentError(error = error, onNavigateToConfig = onNavigateToConfig)
                }

                else -> {
                    Text(
                        text = content.orEmpty(),
                        fontSize = textSize,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState())
                    )

                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                    ) {
                        Slider(
                            value = textSize.value,
                            onValueChange = { textSize = it.sp },
                            valueRange = minTextSize.value..maxTextSize.value,
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .rotate(270f)
                                .offset(y = 80.dp)
                                .padding(end = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

