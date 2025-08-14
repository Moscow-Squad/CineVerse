package com.moscow.cineverse.details.movie_details


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.StorylineSection
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.details.common.DetailsStaffInfoSection
import com.moscow.cineverse.details.common.RecommendationsSection
import com.moscow.cineverse.details.movie_details.component.LoadingContent
import com.moscow.cineverse.details.movie_details.component.MediaItemRatingSection
import com.moscow.cineverse.details.movie_details.component.MovieCastSection
import com.moscow.cineverse.details.movie_details.component.MovieDetailsEffectHandler
import com.moscow.cineverse.details.movie_details.component.MovieHeader
import com.moscow.cineverse.details.movie_details.component.MovieRatingBottomSheetSection
import com.moscow.cineverse.details.movie_details.component.MovieReviewsSection
import com.moscow.cineverse.details.series_details.component.LoginBottomSheet
import com.moscow.cinverse.presentation.R

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToRecommendations: (Int, String) -> Unit,
    navigateToReviews: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateToCollectionsBottomSheet: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            MovieDetailsEffectHandler.handleEffect(
                effect,
                navigateBack = navigateBack,
                navigateToRecommendations = navigateToRecommendations,
                navigateToReviews = navigateToReviews,
                navigateToCastDetails = navigateToCastDetails,
                navigateToCollectionsBottomSheet = navigateToCollectionsBottomSheet,
                navigateToMovieDetails = navigateToMovieDetails,
                navigateToLogin = navigateToLogin,
                context = context
            )
        }
    }

    MovieDetailsContent(
        uiState = uiState,
        interactionListener = viewModel,
        onNavigateBack = navigateBack,
        modifier = modifier,
    )
}

@Composable
private fun MovieDetailsContent(
    uiState: MovieScreenState,
    onNavigateBack: () -> Unit,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier,
) {
    MovieScaffold {
        when {
            uiState.isLoading -> {
                LoadingContent(modifier = modifier)
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
                MovieDetailsMainContent(
                    uiState = uiState,
                    onNavigateBack = onNavigateBack,
                    interactionListener = interactionListener,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationApi::class)
@Composable
private fun MovieDetailsMainContent(
    uiState: MovieScreenState,
    onNavigateBack: () -> Unit,
    interactionListener: MovieDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        scrollState.animateScrollToItem(0)
    }

    LazyColumn(
        state = scrollState,
        modifier = modifier.background(Theme.colors.background.screen)
    ) {
        stickyHeader {
            MovieHeader(
                movieDetailsUiState = uiState.movieDetailsUiState,
                enableBlur = uiState.enableBlur,
                scrollState = scrollState,
                interactionListener = interactionListener,
                onNavigateBack = onNavigateBack,
            )
        }

        item {
            StorylineSection(description = uiState.movieDetailsUiState?.description)
        }
        item {
            uiState.starCast?.let {
                MovieCastSection(
                    castMembers = uiState.starCast,
                    onActorClicked = interactionListener::onActorClicked,
                    enableBlur = uiState.enableBlur
                )
            }
        }
        item {
            DetailsStaffInfoSection(
                characters = uiState.characters,
                director = uiState.director,
                produce = uiState.produce,
                writer = uiState.writer,
            )
        }
        item {
            RecommendationsSection(
                mediaItems = uiState.recommendations,
                onShowMoreClicked = interactionListener::onShowMoreRecommendations,
                onMediaItemClick = { movieId -> interactionListener.onMovieClicked(movieId) },
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
                reviews = uiState.reviewsFlow,
                onShowMoreClicked = {
                    uiState.movieDetailsUiState?.let { interactionListener.onShowMoreReviews(it.id) }
                },
            )
        }
    }

    MovieRatingBottomSheetSection(
        uiState = uiState,
        interactionListener = interactionListener
    )
    LoginBottomSheet(
        isVisible = uiState.showLoginBottomSheet,
        onDismissLoginBottomSheet = interactionListener::onDismissLoginBottomSheet,
        navigateToLogin = interactionListener::navigateToLogin
    )
}