package com.n1cologne.quizgame.domain.model

data class QuizResult(
    val id: Int = 0,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val difficulty: Difficulty
)