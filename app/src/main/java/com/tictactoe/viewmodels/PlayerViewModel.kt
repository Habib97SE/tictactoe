package com.tictactoe.viewmodels

import com.tictactoe.core.Player

class PlayerViewModel {
    var name: String = ""
    var score: Int = 0
    var isWinner: Boolean = false
    var symbol: String = ""

    fun addScore() {
        score++
    }

    private fun resetScore() {
        score = 0
    }

    fun resetWinner() {
        isWinner = false
    }

    fun reset() {
        resetScore()
        resetWinner()
    }

    fun resetAll() {
        reset()
        name = ""
    }

    fun setWinner() {
        isWinner = true
    }

    init {
        this.name = name
        this.score = score
        this.isWinner = isWinner
        this.symbol = symbol
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }

    fun setScore(score: Int) {
        this.score = score
    }

    fun getScore(score: Int): Int {
        return score
    }

    fun setWinner(isWinner: Boolean) {
        this.isWinner = isWinner
    }

    fun setSymbol(symbol: String) {
        this.symbol = symbol
    }

    fun getSymbol(): String {
        return symbol
    }



}