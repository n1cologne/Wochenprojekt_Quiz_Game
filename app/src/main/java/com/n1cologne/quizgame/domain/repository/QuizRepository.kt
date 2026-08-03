package com.n1cologne.quizgame.domain.repository

import com.n1cologne.quizgame.domain.model.QuizQuestion
import com.n1cologne.quizgame.domain.model.QuizSettings

/**
 * Stellt Quizfragen für eine Quizrunde bereit.
 */

interface QuizRepository {

    /**
     * Lädt Quizfragen passend zu den übergebenen Einstellungen.
     *
     * @param settings Anzahl und Schwierigkeit der gewünschten Fragen.
     * @return Die geladenen Fragen oder einen aufgetretenen Fehler.
     */

    suspend fun getQuestions(
        settings: QuizSettings
    ): Result<List<QuizQuestion>>
}