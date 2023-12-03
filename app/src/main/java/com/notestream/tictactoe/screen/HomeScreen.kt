package com.notestream.tictactoe.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(70.dp),
                title = {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "TIC-TAC-TOE",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.ProfileScreen.route)
                        }
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color.DarkGray
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Greeting(name = "Player")
            Column(
                modifier = Modifier
                    .weight(1f) // This makes the column take up only the necessary space
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Play button
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(route = Screen.GameLobbyScreen.route)
                    },
                    shape = CircleShape,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = "Play",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Settings button
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(route = Screen.SettingsScreen.route)
                    },
                    shape = CircleShape,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = "Settings",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                // HowToPlay button
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(route = Screen.HowToPlayScreen.route)
                    },
                    shape = CircleShape,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = "How to play?",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        modifier = Modifier.padding(16.dp)
    )
}
