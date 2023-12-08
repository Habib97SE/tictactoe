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
    private val boardSize = 3
    private val _board =
        MutableStateFlow<Array<Array<String>>>(Array(boardSize) { Array(boardSize) { "" } })
    val board: StateFlow<Array<Array<String>>> = _board

    // controls the game result and update the GameScreen accordingly
    private val _gameState = MutableStateFlow<GameResult?>(null)
    val gameState: StateFlow<GameResult?> = _gameState

    fun updateBoard(row: Int, col: Int, playerSymbol: String) {
        if (_board.value[row][col].isEmpty()) {
            val newBoard = _board.value.copyOf().apply {
                this[row][col] = playerSymbol
            }
            _board.value = newBoard
            checkGameResult()
        }
    }

    fun updateBoard(row: Int, col: Int) {
        viewModelScope.launch {
            SupabaseService.sendTurn(row, col)
            checkGameResult()
        }
    }

    fun releaseMyTurn() {
        viewModelScope.launch {
            SupabaseService.releaseTurn()
        }
    }

    private fun checkGameResult() {
        if (hasWon()) {
            _gameState.value = GameResult.WIN
            finishGame(GameResult.WIN)
        } else if (isDraw()) {
            _gameState.value = GameResult.DRAW
            finishGame(GameResult.DRAW)
        }
    }


    private fun hasWon(): Boolean {
        if (hasWonDiagonally() || hasWonHorizontally() || hasWonVertically()) {
            return true
        }
        return false
    }

    private fun hasWonDiagonally(): Boolean {
        val board = _board.value
        val playerSymbol = board[1][1]
        if (playerSymbol.isEmpty()) {
            return false
        }
        if (board[0][0] == playerSymbol && board[2][2] == playerSymbol) {
            return true
        }
        if (board[0][2] == playerSymbol && board[2][0] == playerSymbol) {
            return true
        }
        return false
    }

    private fun hasWonHorizontally(): Boolean {
        val board = _board.value
        for (row in 0 until boardSize) {
            val playerSymbol = board[row][0]
            if (playerSymbol.isEmpty()) {
                continue
            }
            if (board[row][1] == playerSymbol && board[row][2] == playerSymbol) {
                return true
            }
        }
        return false
    }

    private fun hasWonVertically(): Boolean {
        val board = _board.value
        for (col in 0 until boardSize) {
            val playerSymbol = board[0][col]
            if (playerSymbol.isEmpty()) {
                continue
            }
            if (board[1][col] == playerSymbol && board[2][col] == playerSymbol) {
                return true
            }
        }
        return false
    }

    private fun isDraw(): Boolean {
        val board = _board.value
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (board[row][col].isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    private fun finishGame(gameResult: GameResult) {
        viewModelScope.launch {
            SupabaseService.gameFinish(gameResult)
        }
    }

    fun resetGame() {
        _board.value = Array(boardSize) { Array(boardSize) { "" } }
        _gameState.value = null
    }

}
