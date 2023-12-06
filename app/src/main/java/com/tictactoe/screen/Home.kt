package com.tictactoe.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tictactoe.viewmodels.SharedViewModel


/**
 * Home screen is the main view of the game.
 * The main section has a vertical menu which these items:
 *     1. Play: Take the user further to next screen to find a player and start the game
 *     2. Settings: Adjust settings and other necessary settings that can affect the game playing
 *     3. How to play: A quick guide help screen
 *     4. About Us: A short info about developers
 *     5. Exit: Exit the app
 *
 * On top header, we have the game name and profile icon (clickable)
 * on footer: network connection status and other necessary but not important info
 */
@Composable
fun HomeScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val currentPlayer = sharedViewModel.currentPlayer
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Header(navController)
        Column(
            modifier = Modifier
                .weight(1f) // This makes the column take up only the necessary space
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            AddNewMenuItem(text = "Play", route = Screen.MatchMakingScreen.route, navController)
            AddNewMenuItem(text = "Settings", route = Screen.SettingsScreen.route, navController)
            AddNewMenuItem(text = "How to play?!", route = Screen.HelpScreen.route, navController)
            AddNewMenuItem(text = "About us", route = Screen.AboutScreen.route, navController)
            AddNewMenuItem(text = "Exit", route = "Exit", navController)
        }
        Footer()
    }
}


@Composable
fun AddNewMenuItem(text: String, route: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(onClick = {
            navController.navigate(route)
        }) {
            Text(text = text, color = Color.Black, fontSize = 15.sp)
        }
    }
}