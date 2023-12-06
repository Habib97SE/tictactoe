package com.tictactoe.gameui

import com.tictactoe.core.Board
import com.tictactoe.core.Player

// Use class Board in the "core" package to create the design logic for the game board.
class Game(playerOneName: String, playerTwoName: String) {
    var board: Board = Board()
    var playerOne = Player(playerOneName, 0, false)
    var playerTwo = Player(playerTwoName, 0, false)

    fun reset() {
        board.reset()
        playerOne.reset()
        playerTwo.reset()
    }

    fun resetAll() {
        reset()
        playerOne.resetAll()
        playerTwo.resetAll()
    }

    fun setX(row: Int, col: Int) {
        board.setX(row, col)
    }

    fun setO(row: Int, col: Int) {
        board.setO(row, col)
    }

    fun isBoardFull(): Boolean {
        return board.isBoardFull()
    }

    fun isBoardEmpty(): Boolean {
        return board.isBoardEmpty()
    }

    fun getBoard(): Array<Array<String>> {
        return board.getBoard()
    }



}