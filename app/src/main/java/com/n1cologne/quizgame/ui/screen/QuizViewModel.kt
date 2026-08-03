package com.n1cologne.quizgame.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n1cologne.quizgame.domain.repository.QuizMockRepository
import com.n1cologne.quizgame.domain.model.QuizSettings
import com.n1cologne.quizgame.domain.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository = QuizMockRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState()
    )

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    fun loadQuestions(
        settings: QuizSettings = QuizSettings()
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            repository.getQuestions(settings)
                .onSuccess { questions ->
                    _uiState.value = QuizUiState(
                        questions = questions
                    )
                }
                .onFailure { error ->
                    _uiState.value = QuizUiState(
                        errorMessage = error.message
                            ?: "Die Fragen konnten nicht geladen werden."
                    )
                }
        }
    }

    fun selectAnswer(answer: String) {
        _uiState.value = _uiState.value.copy(
            selectedAnswer = answer
        )
    }
}