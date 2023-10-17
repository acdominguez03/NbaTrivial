package com.example.nbatrivial.presentation.navigation

sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("home_screen")
    object NewQuestionScreen : Screen("new_question_screen")
    object RankingScreen : Screen("ranking_screen")
    object GameScreen : Screen("game_screen")
    object FinalScreen : Screen("final_screen")
}
