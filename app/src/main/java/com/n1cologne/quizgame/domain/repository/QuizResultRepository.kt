package com.n1cologne.quizgame.domain.repository

import com.n1cologne.quizgame.domain.model.QuizResult
import kotlinx.coroutines.flow.Flow

/**
 * Stellt den Zugriff auf gespeicherte Quiz-Ergebnisse bereit.
 */
interface QuizResultRepository {

    /**
     * Gibt alle gespeicherten Quiz-Ergebnisse zurück.
     */
    fun getAllResults(): Flow<List<QuizResult>>

    /**
     * Speichert ein neues Quiz-Ergebnis.
     */
    suspend fun insertResult(result: QuizResult)
}