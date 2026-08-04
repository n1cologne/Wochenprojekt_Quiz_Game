package com.n1cologne.quizgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.domain.model.QuizQuestion
import com.n1cologne.quizgame.ui.theme.QuizGameTheme

@Composable
fun QuizGameScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuizGameContent(
        uiState = uiState,
        onAnswerClick = viewModel::selectAnswer,
        onCheckAnswerClick = viewModel::checkAnswer,
        modifier = modifier
    )
}

@Composable
private fun QuizGameContent(
    uiState: QuizUiState,
    onAnswerClick: (String) -> Unit,
    onCheckAnswerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = uiState.questions
        .getOrNull(uiState.currentQuestionIndex)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage.orEmpty()
                )
            }

            currentQuestion == null -> {
                Text(
                    text = "Keine Fragen verfügbar."
                )
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Frage ${uiState.currentQuestionIndex + 1} " +
                                "von ${uiState.questions.size}"
                    )

                    Text(
                        text = currentQuestion.category,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = currentQuestion.question,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    currentQuestion.answers.forEach { answer ->
                        OutlinedButton(
                            onClick = {
                                onAnswerClick(answer)
                            },
                            enabled = !uiState.isAnswerChecked,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (uiState.selectedAnswer == answer) {
                                    "✓ $answer"
                                } else {
                                    answer
                                }
                            )
                        }
                    }

                    Button(
                        onClick = onCheckAnswerClick,
                        enabled = uiState.selectedAnswer != null &&
                                !uiState.isAnswerChecked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "Check answer")
                    }

                    if (uiState.isAnswerChecked) {
                        val isCorrect =
                            uiState.selectedAnswer == currentQuestion.correctAnswer

                        Text(
                            text = if (isCorrect) {
                                "Richtig!"
                            } else {
                                "Falsch! Richtige Antwort: ${currentQuestion.correctAnswer}"
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun QuizGameScreenPreview() {
    QuizGameTheme {
        QuizGameContent(
            uiState = QuizUiState(
                questions = listOf(
                    QuizQuestion(
                        question = "What is the capital of France?",
                        answers = listOf(
                            "Paris",
                            "London",
                            "Berlin",
                            "Madrid"
                        ),
                        correctAnswer = "Paris",
                        category = "Geography",
                        difficulty = Difficulty.EASY
                    )
                )
            ),
            onAnswerClick = {},
            onCheckAnswerClick = {}
        )
    }
}