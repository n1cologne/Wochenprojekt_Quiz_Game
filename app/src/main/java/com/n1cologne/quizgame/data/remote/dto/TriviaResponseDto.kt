package com.n1cologne.quizgame.data.remote.dto

import com.squareup.moshi.Json

data class TriviaResponseDto(
    @Json(name = "response_code")
    val responseCode: Int,

    @Json(name = "results")
    val results: List<TriviaQuestionDto>
)

data class TriviaQuestionDto(
    @Json(name = "type")
    val type: String,

    @Json(name = "difficulty")
    val difficulty: String,

    @Json(name = "category")
    val category: String,

    @Json(name = "question")
    val question: String,

    @Json(name = "correct_answer")
    val correctAnswer: String,

    @Json(name = "incorrect_answers")
    val incorrectAnswers: List<String>
)