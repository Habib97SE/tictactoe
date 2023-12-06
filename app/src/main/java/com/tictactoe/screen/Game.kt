package com.tictactoe.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tictactoe.viewmodels.BoardViewModel
import com.tictactoe.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController, gameViewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0..2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0..2) {
                    SquareBox(row, col, gameViewModel)
                }
            }
        }
    }
}


@Composable
fun SquareBox(row: Int, col: Int, gameViewModel: GameViewModel) {
    // add state value to update the box content
    var content by remember {
        mutableStateOf("")

    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
            .width(100.dp)
            .height(100.dp)
            .clickable {
                // update the box content
                content = "X"
                // update the board state
                gameViewModel.updateBoardState(row, col)
            },
        contentAlignment = Alignment.Center,
    ) {
        // Add content here (like X or O based on the board state)
        Text(text = content, modifier = Modifier.padding(16.dp), fontSize = 30.sp)

    }
}
