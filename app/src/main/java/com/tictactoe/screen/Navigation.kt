package com.tictactoe.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tictactoe.viewmodels.BoardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {

    val navController = rememberNavController()

    Scaffold(

    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(navController)
            }
            composable(Screen.AboutScreen.route) {
                AboutScreen(navController)
            }
            composable(Screen.ErrorScreen.route) {
                ErrorScreen(navController, "There has been some error!")
            }
            composable(Screen.GameScreen.route) {
                GameScreen(navController, BoardViewModel())
            }
            composable(Screen.GameSummaryScreen.route) {
                GameSummaryScreen(navController)
            }
            composable(Screen.HelpScreen.route) {
                HelpScreen(navController)
            }
            composable(Screen.LoadingScreen.route) {
                LoadingScreen(navController)
            }
            composable(Screen.MatchMakingScreen.route) {
                MatchMakingScreen(navController)
            }
            composable(Screen.ProfileScreen.route) {
                ProfileScreen(navController)
            }
            composable(Screen.SettingsScreen.route) {
                SettingsScreen(navController)
            }
        }
    }

}

