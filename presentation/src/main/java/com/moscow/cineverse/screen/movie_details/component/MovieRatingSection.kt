package com.moscow.cineverse.screen.movie_details.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.mapper.formatReviewDate
import com.moscow.cineverse.screen.movieSeriesDetails.MovieRatingBottomSheet
import com.moscow.cineverse.screen.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.screen.movieSeriesDetails.RatingSection
import com.moscow.cineverse.screen.movie_details.MovieDetailsInteractionListener
import com.moscow.cineverse.screen.movie_details.MovieScreenState

@Composable
fun MovieRatingSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    RatingSection(
        icon = R.drawable.due_tone_star,
        title = stringResource(com.moscow.cinverse.presentation.R.string.give_it_stars),
        caption = stringResource(com.moscow.cinverse.presentation.R.string.let_the_world_know_how_you_felt),
        onClick = { interactionListener.showRatingBottomSheet() },
        ratingStars = uiState.starsRating,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    )
}

@Composable
fun MovieReviewsSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (!uiState.reviewsFlow.isNullOrEmpty()) {
        SectionTitle(
            title = stringResource(com.moscow.cinverse.presentation.R.string.top_reviews),
            onClick = {
                uiState.movieDetailsUiState?.let { movieDetails ->
                    interactionListener.onShowMoreReviews(movieDetails.id)
                }
            },
            modifier = modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp,
            )
        )

        repeat(uiState.reviewsFlow.size) { index ->
            val review = uiState.reviewsFlow[index]
            val userImage = review.userImage

            MovieReviewCard(
                review.name,
                review.username,
                review.reviewContent,
                review.rate,
                formatReviewDate(review.date),
                if (userImage.isEmpty()) null else rememberAsyncImagePainter(model = userImage),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp
                )
            )
        }
    }
}

@Composable
fun MovieRatingBottomSheetSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    uiState.movieDetailsUiState?.let { movieDetails ->
        MovieRatingBottomSheet(
            isVisible = uiState.showRatingBottomSheet,
            onDismiss = { interactionListener.onDismissOrCancelRatingBottomSheet() },
            onRatingSubmit = { rating ->
                interactionListener.onRatingSubmit(rating, movieDetails.id)
            },
            onRatingRemove = {
                interactionListener.onRatingSubmit(0, movieDetails.id)
            },
            initialRating = uiState.starsRating,
            hasExistingRating = uiState.starsRating != 0,
            isLoading = uiState.isLoading,
            modifier = modifier
        )
    }
}
