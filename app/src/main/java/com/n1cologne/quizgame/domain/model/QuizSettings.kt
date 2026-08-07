package com.n1cologne.quizgame.domain.model

data class QuizSettings(
    val questionAmount: Int = 10,
    val difficulty: Difficulty = Difficulty.ANY
)