package com.tictactoe.core

data class GameState(
    val board: Board,
    val currentPlayer: Player,
    val isGameOver: Boolean,
    val winner: Player?,
)
