package com.n1cologne.quizgame.data.repository

import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizQuestion
import com.n1cologne.quizgame.domain.model.QuizSettings
import com.n1cologne.quizgame.domain.repository.QuizRepository

/**
 * Stellt feste Beispieldaten für Entwicklung und Tests bereit.
 */
class QuizMockRepository : QuizRepository {

    private val questions = listOf(
        QuizQuestion(
            question = "What is the capital of France?",
            answers = listOf(
                "Berlin",
                "Paris",
                "London",
                "Madrid"
            ),
            correctAnswer = "Paris",
            category = "Geography",
            difficulty = Difficulty.EASY
        ),
        QuizQuestion(
            question = "Which planet is known as the Red Planet?",
            answers = listOf(
                "Venus",
                "Jupiter",
                "Mars",
                "Mercury"
            ),
            correctAnswer = "Mars",
            category = "Science",
            difficulty = Difficulty.EASY
        ),
        QuizQuestion(
            question = "Who wrote Romeo and Juliet?",
            answers = listOf(
                "Charles Dickens",
                "Jane Austen",
                "Mark Twain",
                "William Shakespeare"
            ),
            correctAnswer = "William Shakespeare",
            category = "Literature",
            difficulty = Difficulty.MEDIUM
        ),
        QuizQuestion(
            question = "What is the chemical symbol for gold?",
            answers = listOf(
                "Au",
                "Ag",
                "Fe",
                "Cu"
            ),
            correctAnswer = "Au",
            category = "Science",
            difficulty = Difficulty.MEDIUM
        ),
        QuizQuestion(
            question = "In which year did the first moon landing happen?",
            answers = listOf(
                "1975",
                "1969",
                "1959",
                "1981"
            ),
            correctAnswer = "1969",
            category = "History",
            difficulty = Difficulty.HARD
        )
    )

    override suspend fun getQuestions(
        settings: QuizSettings
    ): Result<List<QuizQuestion>> {
        val filteredQuestions = if (settings.difficulty == Difficulty.ANY) {
            questions
        } else {
            questions.filter { question ->
                question.difficulty == settings.difficulty
            }
        }

        return Result.success(
            filteredQuestions.take(settings.questionAmount)
        )
    }
}