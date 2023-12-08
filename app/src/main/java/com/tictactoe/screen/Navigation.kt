package com.tictactoe.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tictactoe.network.ServerState
import com.tictactoe.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {

    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()


    Scaffold(

    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.WelcomeScreen.route
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(navController, sharedViewModel)
            }

            composable(Screen.ErrorScreen.route) {
                ErrorScreen(navController, "There has been some error!")
            }
            composable(Screen.GameScreen.route + "/{gameId}") { backStackEntry ->
                val gameId = backStackEntry.arguments?.getString("gameId")
                GameScreen(navController, gameId)
            }
            composable(Screen.GameSummaryScreen.route) {
                GameSummaryScreen(navController)
            }
            composable(Screen.HelpScreen.route) {
                HelpScreen(navController)
            }
            composable(Screen.LoadingScreen.route + "/{message}") { backStackEntry ->
                val message = backStackEntry.arguments?.getString("message")
                if (message != null) {
                    LoadingScreen(navController, message)
                }
            }
            composable(Screen.MatchMakingScreen.route) {
                MatchMakingScreen(navController)
            }
            composable(Screen.ProfileScreen.route) {
                ProfileScreen(navController, sharedViewModel)
            }

            composable(Screen.WelcomeScreen.route) {
                WelcomeScreen(navController, sharedViewModel)
            }

        }
    }

}

