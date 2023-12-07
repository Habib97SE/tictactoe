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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tictactoe.viewmodels.GameViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.tictactoe.network.SupabaseService
import com.tictactoe.viewmodels.SharedViewModel


@Composable
fun GameScreen(navController: NavController, gameId: String? = null) {
    var gameViewModel = GameViewModel()
    var sharedViewModel = viewModel<SharedViewModel>()

    var currentGame = SupabaseService.currentGame

    Row() {
        Text(text = "Game Screen")
        Text(text = "Game ID: ${gameId ?: "null"}")
    }


    val isFirstGame by gameViewModel.isFirstGame.collectAsState()

    if (isFirstGame) {
        // Render UI for the first-time setup
        SetupGameBoardUI(gameViewModel)
    } else {
        // Render UI for an ongoing game
        GameBoardUI(gameViewModel)
    }
}


@Composable
fun GameBoardUI(gameViewModel: GameViewModel) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 30.sp
        )
        GameBoard(board = gameViewModel.board.value ?: emptyArray())
    }
}

@Composable
fun SetupGameBoardUI(gameViewModel: GameViewModel) {
    val board by gameViewModel.board.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Setup Game",
            fontSize = 30.sp
        )
        GameBoard(board = board ?: emptyArray())
    }
}


@Composable
fun GameBoard(board: Array<Array<String>>) {

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
                Text("Game Board")
                for (col in 0..2) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(color = androidx.compose.ui.graphics.Color.LightGray)
                            .width(100.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.Center,

                        ) {
                        // Add content here (like X or O based on the board state)
                        Text(
                            text = board[row][col],
                            modifier = Modifier.padding(16.dp),
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }
    }
}
