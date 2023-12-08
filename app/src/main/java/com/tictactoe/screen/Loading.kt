package com.tictactoe.screen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.tictactoe.network.SupabaseService

@Composable
fun LoadingScreen(navController: NavController, textMessage: String = "Loading ...") {

    var text: String = ""

    if (textMessage == "LOADING_GAME") {
        text = "Loading Game ..."
    } else if (textMessage == "LOADING_LOBBY") {
        text = "Loading Lobby ..."
    } else {
        text = "Loading ..."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingAnimation(textMessage = text)
    }

}


/**
 * ُSource: https://www.youtube.com/watch?v=xakNOVaYLAg&ab_channel=Stevdza-San
 */
@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp,
    textMessage: String = "Loading ..."
) {
    val circles = listOf(
        remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        },
        remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        },
        remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    var lastCircle = circleValues.size - 1
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        circleValues.forEachIndexed { index, value ->
            Box(modifier = Modifier
                .size(circleSize)
                .graphicsLayer {
                    translationY = -value * distance
                }
                .background(
                    color = circleColor,
                    shape = CircleShape
                )
            )
            if (index != lastCircle) {
                Spacer(modifier = Modifier.width(spaceBetween))
            }

        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = textMessage)
    }
}