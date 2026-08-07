package com.n1cologne.quizgame.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.n1cologne.quizgame.data.local.QuizDatabase
import com.n1cologne.quizgame.data.repository.QuizResultRepositoryImpl
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizResult
import com.n1cologne.quizgame.domain.repository.QuizResultRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResultViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dao =
        QuizDatabase
            .getDatabase(application.applicationContext)
            .quizResultDao()

    private val repository: QuizResultRepository =
        QuizResultRepositoryImpl(dao)

    val results: StateFlow<List<QuizResult>> =
        repository
            .getAllResults()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )

    fun insertResult(
        correctAnswers: Int,
        totalQuestions: Int,
        difficulty: Difficulty
    ) {
        viewModelScope.launch {
            repository.insertResult(
                QuizResult(
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions,
                    difficulty = difficulty
                )
            )
        }
    }
}