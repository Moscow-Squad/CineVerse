package com.moscow.cineverse.screen.match.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.screen.match.MatchQuestion

@Composable
fun MatchQuestionsPageContent(
    modifier: Modifier = Modifier,
    onClickNextQuestion: () -> Unit,
    onClickFinishMatching: () -> Unit,
    questions: List<MatchQuestion>,
    currentQuestionIndex: Int,
    onAnswerSelected: (Int, String) -> Unit
) {

}