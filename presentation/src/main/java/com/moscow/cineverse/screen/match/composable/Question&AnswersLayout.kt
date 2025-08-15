package com.moscow.cineverse.screen.match.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.match.QuestionType
import com.moscow.cineverse.screen.match.QuestionUiState

@Composable
fun QuestionAndAnswersLayout(
    questionType: QuestionType,
    questions: List<QuestionUiState>,
    onAnswerSelected: (type: QuestionType, answer: QuestionUiState) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MovieText(
            text = stringResource(questionType.titleResource),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
        )
        when (questionType) {
            QuestionType.MOOD -> {
                questions.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { question ->
                            SelectionCard(
                                onClick = {
                                    onAnswerSelected(questionType, question)
                                },
                                questionUiState = question,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            QuestionType.GENRE -> {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    questions.forEach { question ->
                        SelectionCard(
                            onClick = {
                                onAnswerSelected(questionType, question)
                            },
                            questionUiState = question,
                            itemsPaddingValues = PaddingValues(vertical = 12.dp, horizontal = 20.dp)
                        )
                    }
                }
            }

            QuestionType.TIME -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    questions.forEach { questionUiState ->
                        SelectionCard(
                            onClick = {
                                onAnswerSelected(questionType, questionUiState)
                            },
                            questionUiState = questionUiState,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            QuestionType.TYPE -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    questions.forEach { questionUiState ->
                        SelectionCard(
                            onClick = {
                                onAnswerSelected(questionType, questionUiState)
                            },
                            questionUiState = questionUiState,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }


        }

    }
}