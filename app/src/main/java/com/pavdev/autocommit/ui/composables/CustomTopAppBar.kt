package com.pavdev.autocommit.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavdev.autocommit.data.ConnectionStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    status: ConnectionStatus?,
    sha: String?,
    onSettingsClicked: () -> Unit
) {
    val appBarColor by animateColorAsState(
        label = "StatusColorFade",
        targetValue = status?.color ?: Color.Gray,
        animationSpec = tween(durationMillis = 1000),
    )
    TopAppBar(
        title = {
            Column {
                StatusCrossfade(status = status)
                ShaText(sha = sha)
            }
        },
        actions = {
            SettingsIcon(onClick = onSettingsClicked)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = appBarColor)
    )
}

@Composable
fun StatusCrossfade(status: ConnectionStatus?) {
    Crossfade(
        label = "StatusCrossFade",
        targetState = status,
        animationSpec = tween(1000)
    ) { animatedStatus ->
        Text(text = animatedStatus.toString())
    }
}



@Composable
fun SettingsIcon(onClick: () -> Unit) {
    IconButton(onClick = { /*TODO*/ }) {
        
    }
    // Implementation for settings icon
    // Use the appropriate Icon and IconButton from material3 if available in your project
}