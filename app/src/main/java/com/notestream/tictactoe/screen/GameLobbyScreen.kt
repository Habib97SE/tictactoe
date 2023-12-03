package com.notestream.tictactoe.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/*
Implementation of this screen should be as follows:
1. Show list of all players in the lobby
2. Click on player to challenge him/her for a game
3. ChallengeScreen to show invitations by other players
    - To accept challenge, click the tick
    - To decline challenge, click the cross
4. Challenger should receive a notification to enter the game.
 */
@Composable
fun GameLobbyScreen (navController: NavController){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(70.dp),
                title = {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "GAME LOBBY",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back_icon",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.InvitationScreen.route)
                        }
                    ) {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = "Notifications",
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

        }
    }
}