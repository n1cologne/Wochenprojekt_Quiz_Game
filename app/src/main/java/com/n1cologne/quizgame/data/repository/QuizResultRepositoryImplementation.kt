package com.n1cologne.quizgame.data.repository

import com.n1cologne.quizgame.data.local.dao.QuizResultDao
import com.n1cologne.quizgame.data.mapper.toDomain
import com.n1cologne.quizgame.data.mapper.toEntity
import com.n1cologne.quizgame.domain.model.QuizResult
import com.n1cologne.quizgame.domain.repository.QuizResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Greift über Room auf gespeicherte Quiz-Ergebnisse zu.
 */
class QuizResultRepositoryImpl(
    private val dao: QuizResultDao
) : QuizResultRepository {

    override fun getAllResults(): Flow<List<QuizResult>> {
        return dao.getAllResults()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
    }

    override suspend fun insertResult(result: QuizResult) {
        dao.insertResult(
            result.toEntity()
        )
    }
}