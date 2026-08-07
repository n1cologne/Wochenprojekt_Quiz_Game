package com.n1cologne.quizgame.domain.model

data class QuizSettings(
    val questionAmount: Int = 5,
    val difficulty: Difficulty = Difficulty.ANY
)