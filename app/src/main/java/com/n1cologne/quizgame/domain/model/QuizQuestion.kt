package com.n1cologne.quizgame.domain.model

data class QuizQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswer: String,
    val category: String,
    val difficulty: Difficulty
)