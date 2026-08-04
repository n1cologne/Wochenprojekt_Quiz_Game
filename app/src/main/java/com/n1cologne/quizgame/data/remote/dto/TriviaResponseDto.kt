package com.n1cologne.quizgame.data.remote.dto

import com.squareup.moshi.Json

data class TriviaResponseDto(
    @SerializedName("response_code")
    val responseCode: Int,

    @SerializedName("results")
    val results: List<TriviaQuestionDto>
)

data class TriviaQuestionDto(
    @SerializedName("type")
    val type: String,

    @SerializedName("difficulty")
    val difficulty: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("question")
    val question: String,

    @SerializedName("correct_answer")
    val correctAnswer: String,

    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)