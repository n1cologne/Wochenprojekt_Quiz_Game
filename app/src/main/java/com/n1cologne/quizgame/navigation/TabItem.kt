package com.n1cologne.quizgame.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabItem(
    val route: Any,
    val tabTitle: String,
    val tabIcon: ImageVector
) {
    SETTINGS(
        route = SettingsRoute,
        tabTitle = "Einstellungen",
        tabIcon = Icons.Default.Settings
    ),

    QUIZ_GAME(
        route = QuizGameRoute,
        tabTitle = "QuizGame",
        tabIcon = Icons.Default.PlayArrow
    ),

    RESULT(
        route = ResultRoute,
        tabTitle = "Ergebnisse",
        tabIcon = Icons.Default.Star
    )
}