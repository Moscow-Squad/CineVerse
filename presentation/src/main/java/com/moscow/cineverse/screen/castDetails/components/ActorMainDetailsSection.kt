package com.moscow.cineverse.screen.castDetails.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.InfoSection
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.cast_details.GallerySection
import com.moscow.cineverse.designSystem.component.cast_details.MainDetails
import com.moscow.cineverse.screen.castDetails.CastDetailsInteractionListener
import com.moscow.cineverse.screen.castDetails.CastDetailsUiState
import com.moscow.cineverse.screen.mapper.toFormattedBirthDate
import com.moscow.cineverse.screen.mapper.toMediaItemUi

@Composable
fun ActorMainDetailsSection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    uiState.actorDetails?.let { actorDetails ->
        MainDetails(
            profileImage = actorDetails.profileImg,
            name = actorDetails.name,
            date = actorDetails.birthDate.toFormattedBirthDate(),
            location = actorDetails.placeOfBirth,
            scrollState = null,
            socialMediaLinks = uiState.socialMediaLinks,
            onSocialMediaClick = { platform, url ->
                interactionListener.onSocialMediaClick(platform, url)
            },
            modifier = modifier
        )
    }
}

@Composable
fun ActorMoviesSection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.movies.isNotEmpty()) {
        MovieListSection(
            title = "Best of ${uiState.actorDetails?.name ?: ""}",
            movies = uiState.movies,
            paddingHorizontal = 20,
            onClickShowMore = {
                interactionListener.onShowMoreMovies()
            },
            onClickPoster = { movie ->
                interactionListener.onMovieClick(movie)
            },
            movieCardContent = { movie, cardModifier, onMovieClick ->
                MoviePosterCard(
                    movie = movie.toMediaItemUi(),
                    viewMode = ViewMode.GRID,
                    showRating = true,
                    onMovieClick = { movieId -> onMovieClick(movie) },
                    showTitle = true,
                    modifier = cardModifier
                )
            },
            modifier = modifier
        )
    }
}

@Composable
fun ActorGallerySection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.images.isNotEmpty() && uiState.images.size >= 3) {
        Column(
            modifier = modifier
        ) {
            SectionTitle(
                title = "Gallery",
                onClick = {
                    interactionListener.onShowMoreGallery()
                },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            GallerySection(
                images = uiState.images.take(3),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun ActorBiographySection(
    uiState: CastDetailsUiState,
    modifier: Modifier = Modifier
) {
    uiState.actorDetails?.let { actorDetails ->
        if (actorDetails.biography.isNotEmpty()) {
            Box(
                modifier = modifier
            ) {
                InfoSection(
                    title = "Biography",
                    description = actorDetails.biography,
                    showGenres = false,
                    maxDescriptionLines = Int.MAX_VALUE,
                    paddingBetween = 8.dp,
                    modifier = Modifier.padding(16.dp),
                    showRating = false
                )
            }
        }
    }
}