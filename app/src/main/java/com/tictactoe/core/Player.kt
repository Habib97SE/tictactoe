package com.tictactoe.core


class Player(var name: String, var score: Int = 0, var isWinner: Boolean = false, var symbol: String) {


    fun addScore() {
        score++
    }

    fun reset() {
        score = 0
        isWinner = false
    }

    fun setWinner() {
        isWinner = true
    }
}
