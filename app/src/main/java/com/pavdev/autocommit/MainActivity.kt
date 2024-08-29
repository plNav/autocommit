package com.pavdev.autocommit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pavdev.autocommit.ui.screens.ConfigScreen
import com.pavdev.autocommit.ui.screens.MainScreen
import com.pavdev.autocommit.ui.screens.SettingsScreen
import com.pavdev.autocommit.ui.theme.AutocommitTheme
import com.pavdev.autocommit.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutocommitTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, mainViewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable("main_screen") {
            MainScreen(
                mainViewModel = mainViewModel,
                onNavigateToConfig = {
                    navController.navigate("settings_screen")
                }
            )
        }
        composable("settings_screen") {
            SettingsScreen(
                mainViewModel = mainViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}