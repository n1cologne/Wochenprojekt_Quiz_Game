package com.n1cologne.quizgame.data.repository

import com.n1cologne.quizgame.data.mapper.toQuizQuestion
import com.n1cologne.quizgame.data.remote.TriviaApi
import com.n1cologne.quizgame.data.remote.TriviaApiService
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizQuestion
import com.n1cologne.quizgame.domain.model.QuizSettings
import com.n1cologne.quizgame.domain.repository.QuizRepository

/**
 * Lädt Quizfragen über die OpenTriviaDB API.
 */
class QuizApiRepository(
    private val apiService: TriviaApiService =
        TriviaApi.retrofitService
) : QuizRepository {

    /**
     * Lädt Fragen passend zu den übergebenen Einstellungen.
     *
     * @param settings Anzahl und Schwierigkeit der Fragen.
     * @return Geladene Quizfragen oder einen aufgetretenen Fehler.
     */
    override suspend fun getQuestions(
        settings: QuizSettings
    ): Result<List<QuizQuestion>> {
        return runCatching {
            val response = apiService.getQuestions(
                amount = settings.questionAmount,
                difficulty = settings.difficulty.toApiValue()
            )

            check(response.responseCode == 0) {
                "OpenTriviaDB Fehlercode: ${response.responseCode}"
            }

            response.results.map { questionDto ->
                questionDto.toQuizQuestion()
            }
        }
    }
}

private fun Difficulty.toApiValue(): String? {
    return when (this) {
        Difficulty.ANY -> null
        Difficulty.EASY -> "easy"
        Difficulty.MEDIUM -> "medium"
        Difficulty.HARD -> "hard"
    }
}