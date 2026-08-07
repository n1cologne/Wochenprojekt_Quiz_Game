package com.n1cologne.quizgame.data.mapper

import com.n1cologne.quizgame.data.local.entity.QuizResultEntity
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizResult

fun QuizResult.toEntity(): QuizResultEntity {
    return QuizResultEntity(
        id = id,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        difficulty = difficulty.name
    )
}

fun QuizResultEntity.toDomain(): QuizResult {
    return QuizResult(
        id = id,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        difficulty = Difficulty.valueOf(difficulty)
    )
}