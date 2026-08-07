package com.n1cologne.quizgame.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n1cologne.quizgame.data.repository.QuizApiRepository
import com.n1cologne.quizgame.domain.model.QuizSettings
import com.n1cologne.quizgame.domain.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository = QuizApiRepository()
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
                            ?: "Questions could not be loaded."
                    )
                }
        }
    }

    fun selectAnswer(answer: String) {
        val currentState = _uiState.value

        if (currentState.isAnswerChecked) {
            return
        }

        _uiState.value = _uiState.value.copy(
            selectedAnswer = answer
        )
    }

    fun checkAnswer() {
        val currentState = _uiState.value

        val currentQuestion = currentState.questions
            .getOrNull(currentState.currentQuestionIndex)
            ?: return

        val selectedAnswer = currentState.selectedAnswer
            ?: return

        if (currentState.isAnswerChecked) {
            return
        }

        val isCorrect =
            selectedAnswer == currentQuestion.correctAnswer

        _uiState.value = currentState.copy(
            isAnswerChecked = true,
            correctAnswers = if (isCorrect) {
                currentState.correctAnswers + 1
            } else {
                currentState.correctAnswers
            }
        )
    }

    fun nextQuestion() {
        val currentState = _uiState.value

        if (!currentState.isAnswerChecked) {
            return
        }

        val nextQuestionIndex =
            currentState.currentQuestionIndex + 1

        if (nextQuestionIndex >= currentState.questions.size) {
            _uiState.value = currentState.copy(
                isQuizFinished = true
            )

            return
        }

        _uiState.value = currentState.copy(
            currentQuestionIndex = nextQuestionIndex,
            selectedAnswer = null,
            isAnswerChecked = false
        )
    }

    fun restartQuiz() {
        val currentState = _uiState.value

        _uiState.value = currentState.copy(
            currentQuestionIndex = 0,
            selectedAnswer = null,
            isAnswerChecked = false,
            correctAnswers = 0,
            isQuizFinished = false
        )
    }
}