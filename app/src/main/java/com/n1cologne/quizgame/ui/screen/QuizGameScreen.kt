package com.n1cologne.quizgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect

@Composable
fun QuizGameScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel(),
    onQuizFinished: (Int, Int) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(
        uiState.isQuizFinished,
        uiState.isResultSaved
    ) {
        if (
            uiState.isQuizFinished &&
            !uiState.isResultSaved
        ) {
            onQuizFinished(
                uiState.correctAnswers,
                uiState.questions.size
            )

            viewModel.markResultAsSaved()
        }
    }

    QuizGameContent(
        uiState = uiState,
        onAnswerClick = viewModel::selectAnswer,
        onCheckAnswerClick = viewModel::checkAnswer,
        onNextQuestionClick = viewModel::nextQuestion,
        onRestartQuizClick = viewModel::restartQuiz,
        modifier = modifier
    )
}

@Composable
private fun QuizGameContent(
    uiState: QuizUiState,
    onAnswerClick: (String) -> Unit,
    onCheckAnswerClick: () -> Unit,
    onNextQuestionClick: () -> Unit,
    onRestartQuizClick: () -> Unit,
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

            uiState.isQuizFinished -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Quiz beendet!",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "${uiState.correctAnswers} / " +
                                "${uiState.questions.size} Answers correct! "
                    )

                    Button(
                        onClick = onRestartQuizClick
                    ) {
                        Text(text = "Play again")
                    }
                }
            }

            currentQuestion == null -> {
                Text(
                    text = "No questions available."
                )
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "QuizGame",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Question ${uiState.currentQuestionIndex + 1} " +
                                "of ${uiState.questions.size}"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentQuestion.category,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = currentQuestion.difficulty.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
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
                                    "-> $answer <-"
                                } else {
                                    answer
                                }
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = onCheckAnswerClick,
                        enabled = uiState.selectedAnswer != null &&
                                !uiState.isAnswerChecked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "Check Answer, please")
                    }

                    if (uiState.isAnswerChecked) {
                        val isCorrect =
                            uiState.selectedAnswer == currentQuestion.correctAnswer

                        Text(
                            text = if (isCorrect) {
                                "That's correct!"
                            } else {
                                "That's unfortunately not correct! Correct Answer: ${currentQuestion.correctAnswer}"
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (uiState.isAnswerChecked) {
                        val isLastQuestion =
                            uiState.currentQuestionIndex == uiState.questions.lastIndex

                        Button(
                            onClick = onNextQuestionClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isLastQuestion) {
                                    "QuizGame is over! "
                                } else {
                                    "Next question, please"
                                }
                            )
                        }
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
            onCheckAnswerClick = {},
            onNextQuestionClick = {},
            onRestartQuizClick = {}
        )
    }
}