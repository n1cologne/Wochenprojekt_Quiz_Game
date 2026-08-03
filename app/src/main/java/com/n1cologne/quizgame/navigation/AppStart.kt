package com.n1cologne.quizgame.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.n1cologne.quizgame.ui.screen.QuizGameScreen
import com.n1cologne.quizgame.ui.screen.ResultScreen
import com.n1cologne.quizgame.ui.screen.SettingsScreen

@Composable
fun AppStart(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = QuizGameRoute,
        modifier = modifier
    ) {
        composable<SettingsRoute> {
            SettingsScreen()
        }

        composable<QuizGameRoute> {
            QuizGameScreen()
        }

        composable<ResultRoute> {
            ResultScreen()
        }
    }
}