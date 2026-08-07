package com.n1cologne.quizgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n1cologne.quizgame.domain.model.Difficulty
import com.n1cologne.quizgame.ui.theme.QuizGameTheme

@Composable
fun SettingsScreen(
    selectedDifficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsContent(
        selectedDifficulty = selectedDifficulty,
        onDifficultySelected = onDifficultySelected,
        onApplyClick = onApplyClick,
        modifier = modifier
    )
}

@Composable
private fun SettingsContent(
    selectedDifficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Difficulty",
            style = MaterialTheme.typography.titleMedium
        )

        Difficulty.entries.forEach { difficulty ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDifficulty == difficulty,
                    onClick = {
                        onDifficultySelected(difficulty)
                    }
                )

                Text(
                    text = difficulty.name
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SettingsScreenPreview() {
    QuizGameTheme {
        SettingsContent(
            selectedDifficulty = Difficulty.MEDIUM,
            onDifficultySelected = {},
            onApplyClick = {}
        )
    }
}