package com.moscow.cineverse.screen.movie_details


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.screen.movie_details.component.ErrorContent
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            MovieDetailsEffectHandler.handleEffect(effect, navController)
        }
    }

    MovieDetailsContent(
        uiState = uiState,
        interactionListener = viewModel,
        onNavigateBack = { navController.popBackStack() },
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
            uiState.movieDetailsUi == null -> {
                LoadingContent(modifier = modifier)
            }

            uiState.shouldShowError -> {
                ErrorContent(
                    onRetry = { /* TODO: Add retry logic */ },
                    modifier = modifier
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

@OptIn(ExperimentalSharedTransitionApi::class)
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
            scrollState.firstVisibleItemScrollOffset > 10 || scrollState.firstVisibleItemIndex > 0
        }
    }

    Column(modifier = modifier.background(Theme.colors.background.screen)) {
        MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)

        SharedTransitionLayout {
            AnimatedContent(
                targetState = isCollapsed,
                label = "basic_transition"
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
                MovieStorylineSection(
                    uiState = uiState
                )
            }

            item {
                MovieCastSection(
                    uiState = uiState,
                    interactionListener = interactionListener
                )
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
                MovieRatingSection(
                    uiState = uiState,
                    interactionListener = interactionListener
                )
            }

            item {
                MovieReviewsSection(
                    uiState = uiState,
                    interactionListener = interactionListener
                )
            }
        }

        MovieRatingBottomSheetSection(
            uiState = uiState,
            interactionListener = interactionListener
        )
    }
}