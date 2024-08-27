package com.pavdev.autocommit.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShaText(sha: String?) {
    Crossfade(
        label = "ShaCrossFade",
        targetState = sha,
        animationSpec = tween(1000)
    ) { animatedSha ->
        Text(
            text = animatedSha ?: ".",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            style = TextStyle(fontSize = 10.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}