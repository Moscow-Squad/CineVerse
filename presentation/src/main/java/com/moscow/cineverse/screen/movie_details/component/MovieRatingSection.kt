package com.moscow.cineverse.screen.movie_details.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.card.MessageInfoCard
import com.moscow.cineverse.mapper.formatReviewDate
import com.moscow.cineverse.screen.movieSeriesDetails.MovieRatingBottomSheet
import com.moscow.cineverse.screen.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.screen.movieSeriesDetails.RatingSection
import com.moscow.cineverse.screen.movie_details.MovieDetailsInteractionListener
import com.moscow.cineverse.screen.movie_details.MovieScreenState
import com.moscow.cinverse.presentation.R

@Composable
fun MovieRatingSection(
    uiState: MovieScreenState,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    RatingSection(
        icon = R.drawable.due_tone_star,
        title = stringResource(R.string.give_it_stars),
        caption = stringResource(R.string.let_the_world_know_how_you_felt),
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
            title = stringResource(R.string.top_reviews),
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
            val userImage = review.userImageUrl

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
                interactionListener.onDeleteRatingMovie(movieDetails.id)
            },
            initialRating = uiState.starsRating,
            hasExistingRating = uiState.starsRating != 0,
            isLoading = uiState.isLoading,
            modifier = modifier
        )

        if (uiState.showLoginBottomSheet) {
            CineVerseBottomSheet(
                title = "",
                onClose = { interactionListener.onDismissLoginBottomSheet() },
                onDismissRequest = { interactionListener.onDismissLoginBottomSheet() },
                showCancelIcon = false,
                onAddNewCollectionClick = {}
            ) {
                MessageInfoCard(
                    title = stringResource(R.string.you_re_almost_there),
                    description = stringResource(R.string.log_in_to_save_movies_create_collections_and_get_personalized_recommendations),
                    icon = painterResource(R.drawable.due_tone_login),
                    showButtonsGroup = true,
                    firstButtonText = stringResource(R.string.not_now),
                    onClickFirstButton = { interactionListener.onDismissLoginBottomSheet() },
                    secondButtonText = stringResource(R.string.log_in),
                    onClickSecondButton = { interactionListener.navigateToLogin() },
                )
            }
        }
    }
}