package com.moscow.cineverse.screen.match.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.screen.match.MatchQuestion
import com.moscow.cineverse.screen.match.MatchUiState
import com.moscow.cineverse.screen.match.composable.ProgressIndicator

@Composable
fun MatchQuestionsPageContent(
    state: MatchUiState,
    modifier: Modifier = Modifier,
    onClickNextQuestion: () -> Unit,
    onClickFinishMatching: () -> Unit,
    currentQuestionIndex: Int,
    onAnswerSelected: (Int, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)
        }
    ) {
        ProgressIndicator(
            progress = state.matchProgress,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {

        }
    }

}

