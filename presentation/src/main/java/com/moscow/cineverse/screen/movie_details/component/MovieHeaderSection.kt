package com.moscow.cineverse.screen.movie_details.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MainMovieCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.mapper.toFormattedReleasedDate
import com.moscow.cineverse.mapper.toHourMinuteFormat
import com.moscow.cineverse.screen.movie_details.MovieDetailsInteractionListener
import com.moscow.cineverse.screen.movie_details.MovieScreenState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieHeaderSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    uiState.movieDetailsUiState?.let {
        MovieCardDetails(
            animatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope,
            posterUrl = it.posterPath,
            title = it.title,
            genres = it.genres.joinToString(","),
            rating = it.rating.toString(),
            duration = it.duration.toHourMinuteFormat(),
            releaseDate = it.releaseDate.toFormattedReleasedDate(),
            type = stringResource(com.moscow.cinverse.presentation.R.string.movie),
            onSaveClick = {
                interactionListener.onAddToCollection(it.id)
            },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieCollapsedHeaderSection(
    uiState: MovieScreenState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    interactionListener: MovieDetailsInteractionListener,
) {
    uiState.movieDetailsUiState?.let {
        MainMovieCard(
            posterUrl = it.posterPath,
            title = it.title,
            animatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope,
            onSaveClick = {
                interactionListener.onAddToCollection(it.id)
            }
        )
    }
}

@Composable
fun MovieStorylineSection(
    uiState: MovieScreenState,
    modifier: Modifier = Modifier
) {
    val textColor = Theme.colors.shade.secondary

    uiState.movieDetailsUiState?.let { movieDetails ->
        Text(
            text = stringResource(com.moscow.cinverse.presentation.R.string.storyline),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = modifier.padding(16.dp, top = 24.dp, bottom = 8.dp),
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            overflow = TextOverflow.Ellipsis,
            text = buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 12.sp)) {
                    withStyle(
                        style = SpanStyle(
                            color = textColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            letterSpacing = 0.sp
                        )
                    ) {
                        append(movieDetails.description)
                    }
                }
            },
            textAlign = TextAlign.Justify
        )
    }
}