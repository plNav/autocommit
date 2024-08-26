package com.pavdev.autocommit.data

import androidx.compose.ui.graphics.Color

enum class ConnectionStatus(val color: Color) {
    DISCONNECTED(Color.Red),
    CONNECTING(Color.Yellow),
    CONNECTED(Color.Green),
    FAILED(Color.DarkGray)
}