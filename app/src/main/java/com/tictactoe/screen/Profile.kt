package com.tictactoe.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tictactoe.viewmodels.SharedViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    var currentPlayer = SharedViewModel().currentPlayer

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            Icons.Filled.AccountCircle,
            contentDescription = "Profile Photo",
            modifier = Modifier
                .fillMaxWidth()
                .size(128.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            text = "Username: ${SharedViewModel().currentPlayer.name}"
        )
    }
}