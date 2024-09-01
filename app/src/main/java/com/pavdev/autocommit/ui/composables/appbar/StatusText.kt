package com.pavdev.autocommit.ui.composables.appbar

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.ui.theme.onBackgroundDark

@Composable
fun StatusText(status: ConnectionStatus?) {
    Crossfade(
        label = "StatusCrossFade",
        targetState = status,
        animationSpec = tween(500)
    ) { animatedStatus ->
        Text(
            text = animatedStatus.toString(),
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = onBackgroundDark
            )
        )
    }
}
