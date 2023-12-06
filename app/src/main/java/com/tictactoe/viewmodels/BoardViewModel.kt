package com.tictactoe.viewmodels

import androidx.lifecycle.ViewModel
import com.tictactoe.core.Board

class BoardViewModel : ViewModel() {
    // declare the Board class
    var board: Board = Board()
    fun updateBoardState(row: Int, col: Int) {
        // Update the board state based on the row and column
        board.setBoard(row, col, "X")

    }

    fun getBoxContent(row: Int, col: Int): String {
        // Return the content of the box based on the row and column
        return board.getBoard(row, col)
    }


}