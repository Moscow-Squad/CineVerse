package com.moscow.cineverse.screen.movie_details


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.movie_details.component.LoadingContent
import com.moscow.cineverse.screen.movie_details.component.MovieCastSection
import com.moscow.cineverse.screen.movie_details.component.MovieCollapsedHeaderSection
import com.moscow.cineverse.screen.movie_details.component.MovieDetailsEffectHandler
import com.moscow.cineverse.screen.movie_details.component.MovieHeaderSection
import com.moscow.cineverse.screen.movie_details.component.MovieRatingBottomSheetSection
import com.moscow.cineverse.screen.movie_details.component.MovieRatingSection
import com.moscow.cineverse.screen.movie_details.component.MovieRecommendationsSection
import com.moscow.cineverse.screen.movie_details.component.MovieReviewsSection
import com.moscow.cineverse.screen.movie_details.component.MovieStaffInfoSection
import com.moscow.cineverse.screen.movie_details.component.MovieStorylineSection

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToRecommendations: (Int, String) -> Unit,
    navigateToReviews: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateToCollectionsBottomSheet: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            MovieDetailsEffectHandler.handleEffect(
                effect,
                navigateBack = navigateBack,
                navigateToRecommendations = navigateToRecommendations,
                navigateToReviews = navigateToReviews,
                navigateToCastDetails = navigateToCastDetails,
                navigateToCollectionsBottomSheet = navigateToCollectionsBottomSheet,
                navigateToMovieDetails = navigateToMovieDetails
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
                Box(
                    modifier = Modifier.fillMaxSize().background(Theme.colors.background.screen),
                    contentAlignment = Alignment.Center
                ) {
                    NoInternetScreen(onRetry = interactionListener::onRetry)
                }
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
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 10
        }
    }

    Column(modifier = modifier.background(Theme.colors.background.screen)) {
        MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)

        SharedTransitionLayout {
            AnimatedContent(
                targetState = isCollapsed,
                transitionSpec = {
                    slideInVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        initialOffsetY = { fullHeight -> fullHeight }
                    ) + fadeIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) with
                            slideOutVertically(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                targetOffsetY = { fullHeight -> -fullHeight }
                            ) + fadeOut(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            ) { target ->
                if (!target) {
                    MovieHeaderSection(
                        uiState = uiState,
                        interactionListener = interactionListener,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                } else {
                    MovieCollapsedHeaderSection(
                        uiState = uiState,
                        interactionListener = interactionListener,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier.background(Theme.colors.background.screen)
        ) {
            item {
                MovieStorylineSection(uiState = uiState)
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
                MovieReviewsSection(uiState = uiState, interactionListener = interactionListener)
            }
        }

        MovieRatingBottomSheetSection(
            uiState = uiState,
            interactionListener = interactionListener
        )
    }
}

