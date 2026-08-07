package com.n1cologne.quizgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n1cologne.quizgame.domain.model.QuizResult
import com.n1cologne.quizgame.ui.theme.QuizGameTheme

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = viewModel()
) {
    val results by viewModel.results.collectAsStateWithLifecycle()

    ResultContent(
        results = results,
        modifier = modifier
    )
}

@Composable
private fun ResultContent(
    results: List<QuizResult>,
    modifier: Modifier = Modifier
) {
    if (results.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Noch kein Quiz gespielt.")
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Ergebnisse",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            items(results) { result ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "${result.correctAnswers} von ${result.totalQuestions} richtig"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenPreview() {
    QuizGameTheme {
        ResultContent(
            results = listOf(
                QuizResult(
                    id = 1,
                    correctAnswers = 7,
                    totalQuestions = 10
                ),
                QuizResult(
                    id = 2,
                    correctAnswers = 9,
                    totalQuestions = 10
                )
            )
        )
    }
}