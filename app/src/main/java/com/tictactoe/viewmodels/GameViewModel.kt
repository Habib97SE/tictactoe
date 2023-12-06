import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.tictactoe.network.*

class GameViewModel : ViewModel() {
    val localPlayer: Player = Player()

    init {
        SupabaseService.callbackHandler = object : SupabaseCallback {
            override suspend fun playerReadyHandler() {
                // Logic to handle when the other player is ready

            }

            override suspend fun releaseTurnHandler() {
                // Logic to handle when it's this player's turn
            }

            override suspend fun actionHandler(x: Int, y: Int) {
                // Handle the opponent's action (e.g., their move in Tic Tac Toe)
                opponentMoved(x, y)
            }

            override suspend fun answerHandler(status: ActionResult) {
                // This can be specific to games like Battleships
            }

            override suspend fun finishHandler(status: GameResult) {
                // Handle game finish scenarios
                handleGameFinish(status)
            }
        }

        // Example of observing server state
        viewModelScope.launch {
            SupabaseService.serverState.collect { serverState ->
                handleServerState(serverState)
            }
        }
    }

    // Player actions
    fun joinGame(player: Player) {
        viewModelScope.launch {
            SupabaseService.joinLobby(player)
        }
    }

    fun invitePlayer(opponent: Player) {
        viewModelScope.launch {
            SupabaseService.invite(opponent)
        }
    }

    fun acceptInvitation(game: Game) {
        viewModelScope.launch {
            SupabaseService.acceptInvite(game)
        }
    }

    fun declineInvitation(game: Game) {
        viewModelScope.launch {
            SupabaseService.declineInvite(game)
        }
    }

    fun makeMove(x: Int, y: Int) {
        viewModelScope.launch {
            SupabaseService.sendTurn(x, y)
            SupabaseService.releaseTurn()
        }
    }

    // Private methods for handling responses from SupabaseService
    private fun opponentMoved(x: Int, y: Int) {
        // Update the game state based on the opponent's move
    }

    private fun handleGameFinish(status: GameResult) {
        // Handle game finish logic
    }

    private fun handleServerState(serverState: ServerState) {
        // React to changes in server state
    }

    // Other game-related logic
}
