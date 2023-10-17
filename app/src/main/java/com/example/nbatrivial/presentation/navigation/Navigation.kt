package com.example.nbatrivial.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nbatrivial.presentation.game.FinalScreen
import com.example.nbatrivial.presentation.game.GameScreen
import com.example.nbatrivial.presentation.home.HomeScreen
import com.example.nbatrivial.presentation.questions.NewQuestionScreen
import com.example.nbatrivial.presentation.ranking.RankingScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.NewQuestionScreen.route) {
            NewQuestionScreen()
        }

        composable(route = Screen.RankingScreen.route) {
            RankingScreen()
        }

        composable(route = Screen.GameScreen.route) {
            GameScreen(navController = navController)
        }

        composable(route = Screen.FinalScreen.route) {
            FinalScreen(navController = navController)
        }
    }
}
