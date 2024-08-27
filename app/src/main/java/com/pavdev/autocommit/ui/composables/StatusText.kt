package com.pavdev.autocommit.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pavdev.autocommit.data.ConnectionStatus

@Composable
fun StatusText(status: ConnectionStatus?) {
    Crossfade(
        label = "StatusCrossFade",
        targetState = status,
        animationSpec = tween(1000)
    ) { animatedStatus ->
        Text(text = animatedStatus.toString())
    }
}
