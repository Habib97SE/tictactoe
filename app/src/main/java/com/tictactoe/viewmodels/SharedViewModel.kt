package com.tictactoe.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.tictactoe.network.Game
import kotlinx.coroutines.launch
import com.tictactoe.network.Player
import com.tictactoe.network.ServerState
import com.tictactoe.network.SupabaseService
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {
    private val _invitationResponse = MutableStateFlow<Game?>(null)
    val invitationResponse: StateFlow<Game?> = _invitationResponse
    val serverState: StateFlow<ServerState> = SupabaseService.serverState.asStateFlow()


    var currentPlayer by mutableStateOf(Player(name = "Unknown123HHH"))




    fun updatePlayerName(name: String) {
        currentPlayer = Player(name = name)
    }

    fun onInvitationAccepted(game: Game) {
        viewModelScope.launch {
            _invitationResponse.emit(game)

        }
    }


}