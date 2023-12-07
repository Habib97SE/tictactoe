package com.tictactoe.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tictactoe.network.Player
import com.tictactoe.viewmodels.MatchMakingViewModel
import com.tictactoe.viewmodels.SharedViewModel
import com.tictactoe.network.Game
import com.tictactoe.network.SupabaseService
import kotlinx.coroutines.launch

@Composable
fun MatchMakingScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = viewModel()
) {
    val matchMakingViewModel: MatchMakingViewModel = viewModel()
    val onlineUsers by matchMakingViewModel.onlineUsers.collectAsState()
    val receivedChallenges by SupabaseService.gamesFlow.collectAsState()
    val currentGame by SupabaseService.gameStartEvent.collectAsState()

    // subscribe and listen to changes on gameStartEvent in matchMakingViewModel
    val gameStartEvent by matchMakingViewModel.gameStartEvent.collectAsState()
    LaunchedEffect(currentGame) {
        currentGame?.let { game ->
            // Navigate to the game screen with the game ID or other necessary info
            navController.navigate(Screen.GameScreen.route + "/${game.id}") {
                popUpTo(Screen.MatchMakingScreen.route) {
                    inclusive = true
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Matchmaking Lobby", style = MaterialTheme.typography.headlineMedium)
        }
        Divider()

        //online users:
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (onlineUsers.isEmpty()) {
                Text("No online users")
            } else {
                LazyColumn {
                    items(onlineUsers) { user ->
                        UserItem(user = user) {
                            matchMakingViewModel.sendInvitation(user)
                        }
                    }
                }
            }
        }
        Divider()
        // add invitations:
        if (receivedChallenges.isEmpty()) {
            Text("No invitations")
        } else {
            Row {
                Text(text = "Invitations")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {


                receivedChallenges.forEach { game ->
                    Row() {
                        Text(game.player1.name)
                        Text(" vs ")
                        Text(game.player2.name)
                        TextButton(onClick = {
                            matchMakingViewModel.acceptInvitation(game)
                            navController.navigate("loading/${"GAME_LAODING"}")
                        }) {
                            Text("Accept")
                        }
                        TextButton(onClick = {
                            matchMakingViewModel.dismissInvitation(game)
                        }) {
                            Text("Reject")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserItem(user: Player, onInviteClicked: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var inviteButtonText by remember { mutableStateOf("Invite") }
    var invitationSent by remember { mutableStateOf(false) }
    if (user.name != SharedViewModel().currentPlayer.name) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (user.name != SharedViewModel().currentPlayer.name) {
                if (user.name == "") {
                    Text("Unknown")
                } else {
                    Text(user.name)
                }
            }
            OutlinedButton(onClick = {
                onInviteClicked()
                inviteButtonText = "Invited"
                if (!invitationSent) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Invitation sent",
                            duration = SnackbarDuration.Short
                        )
                    }
                    invitationSent = true
                }
            }) {
                Text(inviteButtonText)
            }

        }
        SnackbarHost(hostState = snackbarHostState) { data ->
            Snackbar(snackbarData = data)
        }
    }
}


@Composable
fun InvitationDialog(game: Game) {
}



