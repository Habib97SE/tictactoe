package com.tictactoe.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow


fun Context.observeConnectivityAsFlow(): Flow<Boolean> {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }
}

@Composable
fun ConnectivityAwareIcon(context: Context) {
    // State to track network availability
    val isConnected = remember { mutableStateOf(true) }

    // Observe connectivity changes
    LaunchedEffect(key1 = Unit) {
        context.observeConnectivityAsFlow().collect { status ->
            isConnected.value = status
        }
    }

    // Decide which icon to display
    val icon = if (isConnected.value) Icons.Default.Add else Icons.Default.Favorite


    Icon(
        imageVector = icon,
        contentDescription = "Connectivity Icon",
        tint = Color.White
    )
}

