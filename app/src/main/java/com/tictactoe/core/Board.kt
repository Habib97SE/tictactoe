package com.tictactoe.core

class Board {
    private var boardState: Array<Array<String>> = arrayOf(
        arrayOf(" ", " ", " "),
        arrayOf(" ", " ", " "),
        arrayOf(" ", " ", " ")
    )

    fun reset() {
        boardState = arrayOf(
            arrayOf(" ", " ", " "),
            arrayOf(" ", " ", " "),
            arrayOf(" ", " ", " ")
        )
    }

    fun resetAll() {
        reset()
    }

    fun setBoard(row: Int, col: Int, value: String) {
        boardState[row][col] = value
    }

    fun getBoard(): Array<Array<String>> {
        return boardState
    }

    fun getBoard(row: Int, col: Int): String {
        return boardState[row][col]
    }

    fun isBoardFull(): Boolean {
        for (row in boardState) {
            for (col in row) {
                if (col == " ") {
                    return false
                }
            }
        }
        return true
    }

    fun isBoardEmpty(): Boolean {
        for (row in boardState) {
            for (col in row) {
                if (col != " ") {
                    return false
                }
            }
        }
        return true
    }

    fun setX(row: Int, col: Int) {
        setBoard(row, col, "X")
    }

    fun setO(row: Int, col: Int) {
        setBoard(row, col, "O")
    }

    fun checkForDraw(): Boolean {
        return isBoardFull()
    }

    /**
     * Check if the board has a winner
     * @return true if there is a winner, false otherwise
     */
    fun checkForWin(): Boolean {
        // if any of the row, col or diagonal is winner, return true else false
        return isWinnerRow() || isWinnerCol() || isWinnerDiagonal()
    }

    /**
     * Check if the board has a winner in any of the columns
     * @return true if there is a winner, false otherwise
     */
    private fun isWinnerCol(): Boolean {
        for (col in 0..2) {
            if (getBoard(0, col) == getBoard(1, col) && getBoard(1, col) == getBoard(2, col)) {
                return true
            }
        }
        return false
    }

    /**
     * Check if the board has a winner in any of the rows
     * @return true if there is a winner, false otherwise
     */
    private fun isWinnerRow(): Boolean {
        for (row in 0..2) {
            if (getBoard(row, 0) == getBoard(row, 1) && getBoard(row, 1) == getBoard(row, 2)) {
                return true
            }
        }
        return false
    }

    /**
     * Check if the board has a winner in any of the diagonals
     * @return true if there is a winner, false otherwise
     */
    private fun isWinnerDiagonal(): Boolean {
        // TODO: implement algorithm for diagonal winner
        return false
    }

    fun isCellEmpty(row: Int, col: Int): Boolean {
        return getBoard(row, col) == " "
    }

    fun isCellX(row: Int, col: Int): Boolean {
        return getBoard(row, col) == "X"
    }

    fun isCellO(row: Int, col: Int): Boolean {
        return getBoard(row, col) == "O"
    }

}