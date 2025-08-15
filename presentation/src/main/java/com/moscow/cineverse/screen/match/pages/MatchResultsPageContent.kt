package com.moscow.cineverse.screen.match.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.screen.details.movie_details.MovieScreenState
import com.moscow.cineverse.screen.match.composable.MatchCarouselAnimation
import com.moscow.cinverse.presentation.R

@Composable
fun MatchResultsPageContent(
    movies: List<MovieScreenState.MovieDetailsUiState>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onSaveClick: (Int) -> Unit,
    onPlayClick: (Int, String) -> Unit
) {
    MovieScaffold(
        modifier = modifier,
        movieAppBar = {
            MovieAppBar(
                title = stringResource(R.string.match_list),
                backButtonClick = onNavigateBack,
                showBackButton = true,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MatchCarouselAnimation(
                movies = movies,
                onMovieClick = onMovieClick,
                onSaveClick = onSaveClick,
                onPlayClick = onPlayClick,
            )
        }
    }
}