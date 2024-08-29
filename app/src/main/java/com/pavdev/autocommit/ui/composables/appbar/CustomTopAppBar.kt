package com.pavdev.autocommit.ui.composables.appbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.pavdev.autocommit.data.enums.ConnectionStatus
import com.pavdev.autocommit.ui.theme.unconnectedLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    status: ConnectionStatus?,
    sha: String?,
    onNavigateSettings: () -> Unit,
) {
    val appBarColor by animateColorAsState(
        label = "StatusColorFade",
        targetValue = status?.getColor() ?: unconnectedLight,
        animationSpec = tween(durationMillis = 1000),
    )
    TopAppBar(
        title = {
            Column {
                StatusText(status = status)
                ShaText(sha = sha)
            }
        },
        actions = {
            SettingsIcon(onNavigateSettings = onNavigateSettings)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = appBarColor)
    )
}



