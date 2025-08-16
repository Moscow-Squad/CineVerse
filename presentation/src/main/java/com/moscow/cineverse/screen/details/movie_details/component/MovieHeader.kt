package com.moscow.cineverse.screen.details.movie_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.details.common.MediaHeader
import com.moscow.cineverse.screen.details.movie_details.MovieDetailsInteractionListener
import com.moscow.cineverse.screen.details.movie_details.MovieScreenState.MovieDetailsUiState

import com.moscow.cinverse.presentation.R

@Composable
fun MovieHeader(
    movieDetailsUiState: MovieDetailsUiState?,
    scrollState: LazyListState,
    onNavigateBack: () -> Unit,
    interactionListener: MovieDetailsInteractionListener,
    enableBlur: String,
) {
    Column(
        modifier = Modifier.background(Theme.colors.background.screen)
    ) {
        MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)
        movieDetailsUiState?.let {
            MediaHeader(
                scrollState = scrollState,
                enableBlur = enableBlur,
                posterUrl = it.posterUrl,
                title = it.title,
                genres = it.genres.joinToString(", "),
                rating = it.rating,
                duration = it.duration,
                releaseDate = it.releaseDate,
                type = stringResource(R.string.movie),
                onSaveClick = { interactionListener.onAddToCollection(it.id) },
                onPlayClick = interactionListener::onPlayButtonClicked,
            )
        }
    }
}