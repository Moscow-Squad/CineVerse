package com.moscow.cineverse.screen.match

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.screen.match.pages.MatchQuestionsPageContent
import com.moscow.cineverse.screen.match.pages.MatchResultsPageContent
import com.moscow.cineverse.screen.match.pages.MatchStartPageContent
import com.moscow.domain.model.MediaType

@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
    viewModel: MatchViewModel = hiltViewModel(),
    navigateToMovieDetails: (id: Int) -> Unit,
    navigateToSeriesDetails: (id: Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MatchEvent.OnClickStartMatching -> {
                    viewModel.onClickStartMatching()
                }

                is MatchEvent.OnClickFinishMatching -> {
                    viewModel.onClickFinishMatching()
                }

                is MatchEvent.OnMediaItemClick -> {
                    when (effect.type) {
                        MediaType.Movie -> navigateToMovieDetails(effect.id)
                        MediaType.Tv -> navigateToSeriesDetails(effect.id)
                        MediaType.Person -> {}
                        MediaType.Unknown -> {}
                    }
                }
            }
        }
    }

    MatchContent(
        state = state,
        listener = viewModel,
        modifier = modifier
    )
}

@Composable
fun MatchContent(
    state: MatchUiState,
    listener: MatchInteractionListener,
    modifier: Modifier = Modifier
) {

    when (state.currentPage) {
        MatchPages.StartPage -> MatchStartPageContent(
            modifier = modifier,
            onClickStartMatching = listener::onClickStartMatching
        )

        MatchPages.QuestionsPage -> MatchQuestionsPageContent(
            modifier = modifier,
            onClickNextQuestion = listener::onClickNextQuestion,
            onClickFinishMatching = listener::onClickFinishMatching,
            state = state,
            currentQuestionIndex = state.currentQuestionIndex,
            onAnswerSelected = listener::onAnswerSelected,
            onNavigateBack = listener::onNavigateBack
        )

        MatchPages.ResultsPage -> MatchResultsPageContent(
            modifier = modifier,
            movies = state.matchResults,
            onMovieClick = listener::onMediaItemClick,
            onNavigateBack = listener::onNavigateBack,
        )
    }

}