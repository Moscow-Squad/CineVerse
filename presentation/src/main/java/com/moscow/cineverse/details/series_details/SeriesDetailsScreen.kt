package com.moscow.cineverse.details.series_details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.component.StorylineSection
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.details.common.DetailsStaffInfoSection
import com.moscow.cineverse.details.common.RecommendationsSection
import com.moscow.cineverse.details.movie_details.component.MediaItemRatingSection
import com.moscow.cineverse.details.movie_details.component.MovieCastSection
import com.moscow.cineverse.details.movie_details.component.MovieReviewsSection
import com.moscow.cineverse.details.series_details.component.LoginBottomSheet
import com.moscow.cineverse.details.series_details.component.SeriesHeader
import com.moscow.cineverse.details.series_details.component.SeriesRatingSection
import com.moscow.cineverse.details.series_details.series_seasons.SeasonSection
import com.moscow.cinverse.presentation.R

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsScreenScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToCollectionBottomSheet: (Int) -> Unit,
    navigateToSeriesRecommendation: (Int, String) -> Unit,
    navigateToReviews: (Int) -> Unit,
    navigateToSeriesSeasons: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            SeriesDetailsEffectHandler.handleSeriesDetailsEvents(
                event = event,
                context = context,
                navigateToCollectionBottomSheet = navigateToCollectionBottomSheet,
                navigateToSeriesRecommendation = navigateToSeriesRecommendation,
                navigateToReviews = navigateToReviews,
                navigateToSeriesSeasons = navigateToSeriesSeasons,
                navigateToCastDetails = navigateToCastDetails,
                navigateToSeriesDetails = navigateToSeriesDetails,
                navigateToLogin = navigateToLogin
            )
        }
    }
    SeriesDetailsContent(
        uiState = uiState,
        interactionListener = viewModel,
        onNavigateBack = navigateBack,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SeriesDetailsContent(
    uiState: SeriesDetailsScreenState,
    interactionListener: SeriesDetailsScreenInteractionListener,
    onNavigateBack: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        scrollState.animateScrollToItem(0)
    }
    MovieScaffold {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen),
                    contentAlignment = Alignment.Center
                ) {
                    MovieCircularProgressBar()
                }
            }

            uiState.shouldShowError -> {
                ErrorContent(
                    errorMessage = uiState.errorMessage,
                    onRetry = interactionListener::onRetry,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                )
            }

            else -> {
                SeriesDetailsMainContent(
                    scrollState = scrollState,
                    interactionListener = interactionListener,
                    uiState = uiState,
                    onNavigateBack = onNavigateBack
                )
                SeriesRatingSection(
                    uiState = uiState,
                    onDeleteRatingSeries = interactionListener::onDeleteRatingSeries,
                    onRatingSubmit = interactionListener::onRatingSubmit,
                    onDismissOrCancelRatingBottomSheet = interactionListener::onDismissOrCancelRatingBottomSheet
                )
                LoginBottomSheet(
                    isVisible = uiState.showLoginBottomSheet,
                    onDismissLoginBottomSheet = interactionListener::onDismissLoginBottomSheet,
                    navigateToLogin = interactionListener::navigateToLogin
                )
            }
        }
    }
}

@Composable
private fun SeriesDetailsMainContent(
    scrollState: LazyListState,
    interactionListener: SeriesDetailsScreenInteractionListener,
    uiState: SeriesDetailsScreenState,
    onNavigateBack: () -> Unit
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        // Make the header section sticky
        stickyHeader {
            SeriesHeader(
                seriesDetails = uiState.seriesDetail,
                enableBlur = uiState.enableBlur,
                scrollState = scrollState,
                interactionListener = interactionListener,
                onNavigateBack = onNavigateBack
            )
        }

        item { StorylineSection(description = uiState.seriesDetail.overview) }

        item {
            SectionTitle(
                title = stringResource(R.string.latest_seasons),
                onClick = interactionListener::onShowMoreSeasonsClicked,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                    top = 24.dp
                )
            )
        }
        item {
            SeasonSection(
                seasons = uiState.seriesDetail.seasons.reversed().take(3),
                enableBlur = uiState.enableBlur
            )
        }
        item {
            MovieCastSection(
                castMembers = uiState.cast,
                enableBlur = uiState.enableBlur,
                onActorClicked = interactionListener::onActorClicked
            )
        }

        if (uiState.crew.isNotEmpty() || uiState.seriesDetail.creators.isNotEmpty()) {
            item {
                DetailsStaffInfoSection(
                    characters = uiState.characters,
                    director = uiState.director,
                    produce = uiState.produce,
                    writer = uiState.writer
                )
            }
        }
        item {
            RecommendationsSection(
                mediaItems = uiState.recommendation,
                onShowMoreClicked = interactionListener::onShowMoreRecommendationsClicked,
                onMediaItemClick = { seriesId -> interactionListener.onSeriesClicked(seriesId) },
                enableBlur = uiState.enableBlur,
            )
        }
        item {
            MediaItemRatingSection(
                starsRating = uiState.starsRating,
                showRatingBottomSheet = interactionListener::showRatingBottomSheet,
            )
        }
        item {
            MovieReviewsSection(
                title = stringResource(R.string.top_reviews),
                reviews = uiState.reviews.take(3),
                onShowMoreClicked = interactionListener::onShowMoreReviewsClicked,
            )
        }

    }
}