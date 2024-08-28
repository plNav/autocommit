package com.pavdev.autocommit.data.enums

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.pavdev.autocommit.ui.theme.connectedDark
import com.pavdev.autocommit.ui.theme.connectedLight
import com.pavdev.autocommit.ui.theme.connectingDark
import com.pavdev.autocommit.ui.theme.connectingLight
import com.pavdev.autocommit.ui.theme.failedDark
import com.pavdev.autocommit.ui.theme.failedLight
import com.pavdev.autocommit.ui.theme.unconnectedDark
import com.pavdev.autocommit.ui.theme.unconnectedLight

enum class ConnectionStatus(private val lightColor: Color, private val darkColor: Color) {
    DISCONNECTED(unconnectedLight, unconnectedDark),
    CONNECTING(connectingLight, connectingDark),
    CONNECTED(connectedLight, connectedDark),
    FAILED(failedLight, failedDark);

    @Composable
    fun getColor(): Color = if (isSystemInDarkTheme()) this.darkColor else lightColor
}