package com.tictactoe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tictactoe.core.Board
import com.tictactoe.core.GameState
import com.tictactoe.core.Player
import com.tictactoe.network.ActionResult
import com.tictactoe.network.Game
import com.tictactoe.network.GameResult
import com.tictactoe.network.SupabaseCallback
import com.tictactoe.network.SupabaseService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {
    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    private val _isFirstGame = MutableStateFlow(true)
    val isFirstGame: StateFlow<Boolean> = _isFirstGame.asStateFlow()

    private val _board = MutableStateFlow(Array(3) { Array(3) { "" } })
    val board: StateFlow<Array<Array<String>>> = _board.asStateFlow()

    val _currentGame = SupabaseService.currentGame

    init {
        if (_currentGame != null) {
            initializeGame(_currentGame)
        }

        SupabaseService.callbackHandler = object : SupabaseCallback {
            override suspend fun playerReadyHandler() {
                onOpponentReady()

            }

            override suspend fun releaseTurnHandler() {
                SupabaseService.releaseTurn()
            }

            override suspend fun actionHandler(x: Int, y: Int) {
                // Update the board with the opponent's move
                val symbol = "O" // Assuming "O" is the opponent's symbol
                updateBoardState(x, y, symbol)
            }

            override suspend fun answerHandler(status: ActionResult) {
                TODO("Not yet implemented")
            }

            override suspend fun finishHandler(status: GameResult) {
                // Handle the game finish scenario based on the status
                handleGameFinish(status)
            }

        }

        // Initialize an empty board and set the starting player
        resetGameState()
    }

    private fun initializeGame(game: Game) {

        if (_isFirstGame.value) {
            // Set up the initial state of the board
            setupInitialBoard()
            _isFirstGame.value = false
        }
    }

    private fun setupInitialBoard() {
        // Example implementation
        viewModelScope.launch {
            // Assuming 'currentGame' holds the ongoing game's state
            val currentGame = SupabaseService.currentGame
            if (currentGame != null) {
                // Update the local game state to reflect the initial board state
                val initialState = GameState(
                    board = Board(),
                    currentPlayer = determineStartingPlayer(currentGame),
                    isGameOver = false,
                    winner = null
                )
                _gameState.value = initialState

                // Additional logic to transition from lobby to game screen, if needed
                // ...
            }
        }
    }

    private fun onOpponentReady() {
        // Example implementation
        viewModelScope.launch {
            // Assuming 'currentGame' holds the ongoing game's state
            val currentGame = SupabaseService.currentGame
            if (currentGame != null) {
                // Update the local game state to reflect that both players are ready
                val initialState = GameState(
                    board = Board(),
                    currentPlayer = determineStartingPlayer(currentGame),
                    isGameOver = false,
                    winner = null
                )
                _gameState.value = initialState

                // Additional logic to transition from lobby to game screen, if needed
                // ...
            }
        }
    }

    private fun determineStartingPlayer(game: Game): Player {
        // Logic to determine who starts the game
        // This can depend on your game's rules or the data in 'game'
        // Example:
        return if (game.player1.id == SupabaseService.player?.id) {
            Player(game.player1.name, symbol = "X")
        } else {
            Player(game.player2.name, symbol = "O")
        }
    }

    private fun resetGameState() {
        _gameState.value = GameState(
            board = Board(),
            currentPlayer = Player("Player1", symbol = "X"), // Example initialization
            isGameOver = false,
            winner = null
        )
    }

    private fun updateBoardState(row: Int, col: Int, symbol: String) {
        _gameState.value?.let { state ->
            if (!state.isGameOver && state.board.isCellEmpty(row, col)) {
                state.board.setBoard(row, col, symbol)
                if (state.board.checkForWin()) {
                    _gameState.value = state.copy(isGameOver = true, winner = state.currentPlayer)
                } else if (state.board.checkForDraw()) {
                    _gameState.value = state.copy(isGameOver = true)
                } else {
                    // Change the current player for the next turn
                    val nextPlayer = if (state.currentPlayer.symbol == "X") "O" else "X"
                    _gameState.value =
                        state.copy(currentPlayer = Player("Player2", symbol = nextPlayer))
                }
            }
        }
    }

    private fun handleGameFinish(status: GameResult) {
        viewModelScope.launch {
            when (status) {
                GameResult.WIN -> {
                    // Update the game state to reflect the win
                    _gameState.value = _gameState.value?.copy(
                        isGameOver = true,
                        winner = _gameState.value?.currentPlayer // Assuming the current player is the winner
                    )
                    // Trigger any UI updates or display a winning message

                }

                GameResult.LOSE -> {
                    // Update the game state to reflect the loss
                    _gameState.value = _gameState.value?.copy(
                        isGameOver = true,
                        winner = getOpponentPlayer() // Get the opponent as the winner
                    )
                    // Trigger any UI updates or display a losing message
                }

                GameResult.DRAW -> {
                    // Update the game state to reflect the draw
                    _gameState.value = _gameState.value?.copy(isGameOver = true, winner = null)
                    // Trigger any UI updates or display a draw message
                }

                GameResult.SURRENDER -> {
                    // Update the game state to reflect the surrender
                    _gameState.value = _gameState.value?.copy(
                        isGameOver = true,
                        winner = getOpponentPlayer() // Get the opponent as the winner
                    )
                    // Trigger any UI updates or display a surrender message
                }

                else -> {
                    // Handle any other game finish scenarios
                }
            }
            delay(3000)
            resetGameState()
        }
    }

    private fun getOpponentPlayer(): Player {
        // Implement logic to return the opponent player
        // This will depend on how you track the opponent player in your game
        // Example:
        return Player("Player2", symbol = "O")
    }


    fun makeMove(row: Int, col: Int, playerSymbol: String) {
        if (_board.value[row][col] == "") {
            _board.value[row][col] = playerSymbol
            if (checkForWin()) {
                // Update the game state to reflect the win
                _gameState.value = _gameState.value?.copy(
                    isGameOver = true,
                    winner = _gameState.value?.currentPlayer // Assuming the current player is the winner
                )
                // Trigger any UI updates or display a winning message
            } else if (checkForDraw()) {
                // Update the game state to reflect the draw
                _gameState.value = _gameState.value?.copy(isGameOver = true, winner = null)
                // Trigger any UI updates or display a draw message
            } else {
                // Change the current player for the next turn
                val nextPlayer = if (_gameState.value?.currentPlayer?.symbol == "X") "O" else "X"
                _gameState.value =
                    _gameState.value?.copy(currentPlayer = Player("Player2", symbol = nextPlayer))
            }
        }
    }

    fun checkForWin(): Boolean {
        if (winInRow(0, "X") || winInRow(1, "X") || winInRow(2, "X")) {
            return true
        }
        if (winInCol(0, "X") || winInCol(1, "X") || winInCol(2, "X")) {
            return true
        }
        if (winInDiagonal("X")) {
            return true
        }
        return false
    }

    fun winInRow(row: Int, playerSymbol: String): Boolean {

        if (_board.value[row][0] == playerSymbol && _board.value[row][1] == playerSymbol && _board.value[row][2] == playerSymbol) {
            return true
        }
        return false
    }

    fun winInCol(col: Int, playerSymbol: String): Boolean {

        if (_board.value[0][col] == playerSymbol && _board.value[1][col] == playerSymbol && _board.value[2][col] == playerSymbol) {
            return true
        }
        return false
    }

    fun winInDiagonal(playerSymbol: String): Boolean {

        if (_board.value[0][0] == playerSymbol && _board.value[1][1] == playerSymbol && _board.value[2][2] == playerSymbol) {
            return true
        }
        if (_board.value[0][2] == playerSymbol && _board.value[1][1] == playerSymbol && _board.value[2][0] == playerSymbol) {
            return true
        }
        return false
    }

    fun checkForDraw(): Boolean {
        for (row in 0..2) {
            for (col in 0..2) {
                if (_board.value[row][col] == "") {
                    return false
                }
            }
        }
        return true
    }


    // Additional methods like joinGame, invitePlayer, declineInvitation...
}
