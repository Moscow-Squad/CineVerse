package com.moscow.cineverse.screen.movie_details.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CastCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.movie_details.MovieDetailsInteractionListener
import com.moscow.cineverse.screen.movie_details.MovieScreenState
import com.moscow.cinverse.presentation.R

@Composable
fun MovieCastSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(!uiState.starCast.isNullOrEmpty()) {
        StarCastSection(
            title = stringResource(com.moscow.cinverse.presentation.R.string.star_cast),
            modifier = modifier
                .background(Theme.colors.background.screen)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            cast = uiState.starCast?.take(6) ?: emptyList(),
            castContent = { actor ->
                CastCard(
                    modifier = Modifier.clickable {
                        interactionListener.onActorClicked(actor.id)
                    },
                    castMember = actor,
                    getOriginalName = { it.originalName },
                    getCharacterName = { it.characterName },
                    getProfileImage = { it.profileImage }
                )
            }
        )
    }
}

@Composable
fun MovieStaffInfoSection(
    uiState: MovieScreenState,
    modifier: Modifier = Modifier
) {
    StaffInfoSection(
        staffInfo = listOf(
            stringResource(R.string.characters) to uiState.characters.joinToString(","),
            stringResource(R.string.director_screenplay_story) to uiState.director.joinToString(","),
            stringResource(R.string.producer) to uiState.produce.joinToString(","),
            stringResource(R.string.writer) to uiState.writer.joinToString(",")
        ),
        modifier = modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
    )
}

@Composable
fun MovieRecommendationsSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    uiState.movieDetailsUiState?.let { movieDetails ->
        AnimatedVisibility(uiState.recommendations.isNotEmpty()) {
            MovieListSection(
                title = stringResource(com.moscow.cinverse.presentation.R.string.you_might_also_like),
                movies = uiState.recommendations,
                onClickShowMore = {
                    interactionListener.onShowMoreRecommendations(
                        movieDetails.id,
                        movieDetails.title
                    )
                },
                onClickPoster = { movie -> },
                modifier = modifier.padding(top = 16.dp),
                movieCardContent = { movie, cardModifier, onClick ->
                    MoviePosterCard(
                        movie = movie,
                        viewMode = ViewMode.GRID,
                        showRating = true,
                        onMovieClick = {interactionListener.onMovieClicked(movie.id)},
                        showTitle = true,
                        modifier = cardModifier,
                        getTitleOverride = { it.title.take(15) + if (it.title.length > 15) "…" else "" }
                    )
                }
            )
        }
    }
}