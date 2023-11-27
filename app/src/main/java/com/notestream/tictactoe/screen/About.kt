package com.notestream.tictactoe.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(text = "About us", color = Color.Black, fontSize = 32.sp)
        }
        Row() {
            Text(
                text = "This is a dummy text to preshow the final result appearance and color.\n This is a dummy text to preshow the final result appearance and color. This is a dummy text to preshow the final result appearance and color.\nThis is a dummy text to preshow the final result appearance and color.",
                color = Color.Black, fontSize = 16.sp
            )
        }
    }
}

