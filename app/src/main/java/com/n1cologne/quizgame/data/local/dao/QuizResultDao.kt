package com.n1cologne.quizgame.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.n1cologne.quizgame.data.local.entity.QuizResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResult(
        result: QuizResultEntity
    )

    @Query(
        "SELECT * FROM quiz_results ORDER BY id DESC"
    )
    fun getAllResults(): Flow<List<QuizResultEntity>>
}