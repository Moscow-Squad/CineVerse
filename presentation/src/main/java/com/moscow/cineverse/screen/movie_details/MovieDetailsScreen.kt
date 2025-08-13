package com.moscow.cineverse.screen.movie_details


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.StorylineSection
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.mapper.toFormattedReleasedDate
import com.moscow.cineverse.mapper.toHourMinuteFormat
import com.moscow.cineverse.screen.movieSeriesDetails.MediaHeader
import com.moscow.cineverse.screen.movie_details.component.LoadingContent
import com.moscow.cineverse.screen.movie_details.component.MovieCastSection
import com.moscow.cineverse.screen.movie_details.component.MovieDetailsEffectHandler
import com.moscow.cineverse.screen.movie_details.component.MovieRatingBottomSheetSection
import com.moscow.cineverse.screen.movie_details.component.MovieRatingSection
import com.moscow.cineverse.screen.movie_details.component.MovieRecommendationsSection
import com.moscow.cineverse.screen.movie_details.component.MovieReviewsSection
import com.moscow.cineverse.screen.movie_details.component.MovieStaffInfoSection
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
    modifier: Modifier = Modifier
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
            Column(
                modifier = Modifier.background(Theme.colors.background.screen)
            ) {
                MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)
                uiState.movieDetailsUiState?.let {
                    MediaHeader(
                        scrollState = scrollState,
                        enableBlur = uiState.enableBlur,
                        posterUrl = it.posterUrl,
                        title = it.title,
                        genres = it.genres.joinToString(", "),
                        rating = it.rating.toString(),
                        duration = it.duration.toHourMinuteFormat(LocalContext.current),
                        releaseDate = it.releaseDate?.toFormattedReleasedDate()?:"",
                        type = stringResource(R.string.movie),
                        onSaveClick = {
                            interactionListener.onAddToCollection(it.id)
                        },
                        onPlayClick = interactionListener::onPlayButtonClicked,
                    )
                }
            }
        }

        item {
            StorylineSection(description = uiState.movieDetailsUiState?.description)
        }
        item {
            MovieCastSection(uiState = uiState, interactionListener = interactionListener)
        }
        item {
            MovieStaffInfoSection(uiState = uiState)
        }
        item {
            MovieRecommendationsSection(
                uiState = uiState,
                interactionListener = interactionListener
            )
        }
        item {
            MovieRatingSection(uiState = uiState, interactionListener = interactionListener)
        }
        item {
            if (uiState.reviewsFlow?.isEmpty() == true) {
                Spacer(modifier = Modifier.height(50.dp))
            } else {
                MovieReviewsSection(uiState = uiState, interactionListener = interactionListener)
            }
        }
    }

    MovieRatingBottomSheetSection(
        uiState = uiState,
        interactionListener = interactionListener
    )
}