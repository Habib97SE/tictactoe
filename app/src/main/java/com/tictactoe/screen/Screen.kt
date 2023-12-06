package com.tictactoe.screen

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object SettingsScreen : Screen("settings")
    object ProfileScreen : Screen("profile")
    object GameScreen : Screen("game")
    object MatchMakingScreen : Screen("match_making")
    object GameSummaryScreen : Screen("game_summary")
    object HelpScreen : Screen("help")
    object AboutScreen : Screen("about")
    object ErrorScreen : Screen("error")
    object LoadingScreen : Screen("loading")

}