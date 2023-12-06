package com.tictactoe.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tictactoe.network.Player
import com.tictactoe.viewmodels.MatchMakingViewModel
import com.tictactoe.viewmodels.SharedViewModel
import com.tictactoe.network.Game

@Composable
fun MatchMakingScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = viewModel()
) {
    val matchMakingViewModel: MatchMakingViewModel = viewModel()
    val onlineUsers by matchMakingViewModel.onlineUsers.collectAsState(initial = emptyList())




    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Matchmaking Lobby", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        
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


    }

    //if there is invitations, show the dialog
    val invitations by matchMakingViewModel.invitations.collectAsState(initial = emptyList())
    if (invitations.isNotEmpty()) {
        InvitationDialog()
    }

    // if there is invitationResponses, show the dialog
    val invitationResponses by matchMakingViewModel.invitationResponses.collectAsState(initial = null)
    invitationResponses?.let { game ->
        AlertDialog(
            onDismissRequest = { matchMakingViewModel.dismissInvitation(game) },
            title = { Text(text = "Invitation") },
            text = { Text(text = "You have been invited to play with ${game.player1.name}") },
            confirmButton = {
                Button(onClick = {
                    matchMakingViewModel.acceptInvitation(game)
                }) {
                    Text(text = "Accept")
                }
            },
            dismissButton = {
                Button(onClick = {
                    matchMakingViewModel.dismissInvitation(game)
                }) {
                    Text(text = "Decline")
                }
            }
        )
    }


}


@Composable
fun UserItem(user: Player, onInviteClicked: () -> Unit) {
    if (user.name != SharedViewModel().currentPlayer.name) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (user.name == "") {
                Text("Anonymous")
            } else {
                Text(user.name)
            }
            OutlinedButton(onClick = { onInviteClicked() }) {
                Text("Invite")
            }
        }
    }
}


@Composable
fun InvitationDialog(viewModel: MatchMakingViewModel = viewModel()) {
    val invitationResponses by viewModel.invitationResponses.collectAsState(initial = null)
    invitationResponses?.let { game ->
        AlertDialog(
            onDismissRequest = { viewModel.dismissInvitation(game) },
            title = { Text(text = "Invitation") },
            text = { Text(text = "You have been invited to play with ${game.player1.name}") },
            confirmButton = {
                Button(onClick = {
                    viewModel.acceptInvitation(game)
                }) {
                    Text(text = "Accept")
                }
            },
            dismissButton = {
                Button(onClick = {
                    viewModel.dismissInvitation(game)
                }) {
                    Text(text = "Decline")
                }
            }
        )
    }
}
