package com.n1cologne.quizgame.ui.screen

import com.n1cologne.quizgame.domain.model.QuizQuestion

data class QuizUiState(
    val isLoading: Boolean = false,
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val isAnswerChecked: Boolean = false,
    val correctAnswers: Int = 0,
    val isQuizFinished: Boolean = false,
    val errorMessage: String? = null
)