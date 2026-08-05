package com.n1cologne.quizgame.data.mapper

import androidx.core.text.HtmlCompat
import com.n1cologne.quizgame.data.remote.dto.TriviaQuestionDto
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizQuestion

fun TriviaQuestionDto.toQuizQuestion(): QuizQuestion {
    val decodedCorrectAnswer = correctAnswer.decodeHtml()

    val decodedAnswers = incorrectAnswers
        .map { answer ->
            answer.decodeHtml()
        }
        .plus(decodedCorrectAnswer)
        .shuffled()


    return QuizQuestion(
        question = question.decodeHtml(),
        answers = decodedAnswers,
        correctAnswer = decodedCorrectAnswer,
        category = category.decodeHtml(),
        difficulty = difficulty.toDifficulty()
    )
}

private fun String.decodeHtml(): String {
    return HtmlCompat.fromHtml(
        this,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()
}

private fun String.toDifficulty(): Difficulty {
    return when (lowercase()) {
        "easy" -> Difficulty.EASY
        "medium" -> Difficulty.MEDIUM
        "hard" -> Difficulty.HARD
        else -> Difficulty.ANY
    }
}