package com.n1cologne.quizgame.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.n1cologne.quizgame.data.local.dao.QuizResultDao
import com.n1cologne.quizgame.data.local.entity.QuizResultEntity

@Database(
    entities = [QuizResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun quizResultDao(): QuizResultDao

    companion object {

        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                    .build()
                    .also { database ->
                        INSTANCE = database
                    }
            }
        }
    }
}