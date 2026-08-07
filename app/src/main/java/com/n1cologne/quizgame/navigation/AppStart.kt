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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizSettings
import com.n1cologne.quizgame.ui.screen.QuizViewModel
import com.n1cologne.quizgame.ui.screen.ResultViewModel

@Composable
fun AppStart(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val quizViewModel: QuizViewModel = viewModel()
    val resultViewModel: ResultViewModel = viewModel()

    var selectedTab by rememberSaveable {
        mutableStateOf(TabItem.QUIZ_GAME)
    }

    var selectedDifficulty by rememberSaveable {
        mutableStateOf(Difficulty.ANY)
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
                SettingsScreen(
                    selectedDifficulty = selectedDifficulty,
                    onDifficultySelected = { difficulty ->
                        selectedDifficulty = difficulty
                    },
                    onApplyClick = {
                        quizViewModel.loadQuestions(
                            QuizSettings(
                                difficulty = selectedDifficulty
                            )
                        )

                        selectedTab = TabItem.QUIZ_GAME
                    }
                )
            }

            composable<QuizGameRoute> {
                QuizGameScreen(
                    viewModel = quizViewModel,
                    onQuizFinished = { correctAnswers, totalQuestions ->
                        resultViewModel.insertResult(
                            correctAnswers = correctAnswers,
                            totalQuestions = totalQuestions,
                            difficulty = selectedDifficulty
                        )
                    }
                )
            }

            composable<ResultRoute> {
                ResultScreen(
                    viewModel = resultViewModel
                )
            }
        }
    }
}