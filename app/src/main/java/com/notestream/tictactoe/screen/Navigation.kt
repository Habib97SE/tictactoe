package com.notestream.tictactoe.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.garrit.android.multiplayer.ServerState
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.AboutScreen.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.ErrorScreen.route) {
            ErrorScreen(navController, "There has been some error!")
        }
        composable(route = Screen.GameScreen.route) {
            GameScreen(navController)
        }
        composable(route = Screen.GameSummaryScreen.route) {
            GameSummaryScreen(navController)
        }
        composable(route = Screen.HowToPlayScreen.route) {
            HowToPlayScreen(navController)
        }
        composable(route = Screen.LoadingScreen.route) {
            LoadingScreen(navController)
        }
        composable(route = Screen.GameLobbyScreen.route) {
            GameLobbyScreen(navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        composable(route = Screen.InvitationScreen.route) {
            InvitationScreen(navController)
        }
    }

}

