package com.pavdev.autocommit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.pavdev.autocommit.ui.screens.MainScreen
import com.pavdev.autocommit.ui.theme.AutocommitTheme
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutocommitTheme {
               MainScreen(mainViewModel)
            }
        }
    }
}




