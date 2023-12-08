package com.tictactoe.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // when opponent sends playerReady, navigate to game screen
    val opponentReady by sharedViewModel.opponentReady.collectAsState()
    if (opponentReady) {
        val gameId = SupabaseService.currentGame?.id
        LaunchedEffect(Unit) {
            navController.navigate(Screen.GameScreen.route + "/${gameId}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(70.dp),
                title = {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "LOBBY",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back_icon",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.ProfileScreen.route)
                        }
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle, contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                },

                backgroundColor = Color.DarkGray
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    //online users:
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Online Users")
                        }
                        if (onlineUsers.size == 1 && onlineUsers[0].name == sharedViewModel.currentPlayer.name) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("No online users", fontSize = 24.sp)
                            }
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
                Divider()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    // add invitations:
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Invitations")
                    }
                    if (receivedChallenges.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No invitations", fontSize = 24.sp)
                        }
                    } else {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            receivedChallenges.forEach { game ->
                                InvitationListItem(game = game, navController = navController)
                            }
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
fun InvitationListItem(game: Game, navController: NavController) {
    val matchMakingViewModel: MatchMakingViewModel = viewModel()
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



