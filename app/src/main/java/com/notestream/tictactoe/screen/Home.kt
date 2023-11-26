package com.notestream.tictactoe.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.w3c.dom.Text


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
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Header()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AddNewMenuItem(text = "Play")
            AddNewMenuItem(text = "Settings")
            AddNewMenuItem(text = "How to play?!")
            AddNewMenuItem(text = "About us")
            AddNewMenuItem(text = "Exit")
        }
    }
}


@Composable
fun AddNewMenuItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = text, color = Color.Black, fontSize = 15.sp)
        }
    }
}