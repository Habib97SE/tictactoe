    package com.tictactoe.viewmodels

    import androidx.compose.runtime.snapshots.SnapshotStateList
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import androidx.navigation.compose.rememberNavController
    import com.tictactoe.network.Player
    import com.tictactoe.network.SupabaseService
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import com.tictactoe.network.Game
    import com.tictactoe.network.ServerState
    import com.tictactoe.network.SupabaseCallback
    import com.tictactoe.viewmodels.SharedViewModel

    class MatchMakingViewModel : ViewModel() {
        private val _onlineUsers = MutableStateFlow<List<Player>>(emptyList())
        val onlineUsers: StateFlow<List<Player>> = _onlineUsers
        val invitationResponses: StateFlow<Game?> = SupabaseService.invitationResponses
        val sharedViewModel = SharedViewModel()
        val invitations: StateFlow<List<Game>> = SupabaseService.gamesFlow
        val _gameStartEvent = MutableStateFlow<Game?>(null)
        val gameStartEvent: StateFlow<Game?> = _gameStartEvent

        init {
            joinLobby()
            fetchOnlineUsers()
            setupInvitationResponseListener()
        }

        private fun setupInvitationResponseListener() {
            viewModelScope.launch {
                SupabaseService.invitationResponses.collect { game ->
                    if (game != null && game.player1.id == sharedViewModel.currentPlayer.id) {
                        // The invitation you sent has been accepted
                        // You can now proceed to start the game
                        onInvitationAccepted(game)
                    }
                }
            }
        }

        private fun onInvitationAccepted(game: Game) {
            viewModelScope.launch {
                prepareGameState(game)
                SupabaseService.playerReady()
                // change gameStartEvent to the current game
                _gameStartEvent.value = game
            }
        }

        private fun joinLobby() {
            viewModelScope.launch {
                // get current player from SharedViewModel
                val localPlayer = sharedViewModel.currentPlayer
                SupabaseService.joinLobby(localPlayer)
            }
        }

        private fun fetchOnlineUsers() {
            viewModelScope.launch {
                SupabaseService.usersFlow.collect { usersList ->
                    _onlineUsers.value = usersList
                }
            }
        }

        fun sendInvitation(toPlayer: Player) {
            viewModelScope.launch {
                SupabaseService.invite(toPlayer)

            }
        }

        fun acceptInvitation(game: Game) {
            viewModelScope.launch {
                SupabaseService.acceptInvite(game)
                SupabaseService.playerReady()
            }
        }

        private fun declineInvitation(game: Game) {
            viewModelScope.launch {
                SupabaseService.declineInvite(game)
                // remove the invitation from the list of invitations
                // so that it is no longer displayed
                invitations.value.toMutableList().remove(game)
            }
        }

        fun dismissInvitation(game: Game) {
            declineInvitation(game)
        }

        private fun prepareGameState(game: Game) {
            viewModelScope.launch {
                _gameStartEvent.value = game
            }
        }





    }
