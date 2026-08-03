package com.n1cologne.quizgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.n1cologne.quizgame.ui.theme.QuizGameTheme

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Ergebnis")

        Text(text = "Noch kein Quiz gespielt.")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ResultScreenPreview() {
    QuizGameTheme {
        ResultScreen()
    }
}