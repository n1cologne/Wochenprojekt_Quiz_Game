package com.n1cologne.quizgame.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

    var selectedTab by rememberSaveable {
        mutableStateOf(TabItem.QUIZ_GAME)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                TabItem.entries.forEach { tabItem ->
                    NavigationBarItem(
                        selected = selectedTab == tabItem,
                        onClick = {
                            selectedTab = tabItem
                        },
                        icon = {
                            Icon(
                                imageVector = tabItem.tabIcon,
                                contentDescription = tabItem.tabTitle
                            )
                        },
                        label = {
                            Text(text = tabItem.tabTitle)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = selectedTab.route,
            modifier = Modifier.padding(innerPadding)
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
}