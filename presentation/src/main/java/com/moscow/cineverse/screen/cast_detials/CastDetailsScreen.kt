package com.moscow.cineverse.screen.cast_detials

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.EmptyState
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.screen.cast_detials.components.ActorBiographySection
import com.moscow.cineverse.screen.cast_detials.components.ActorGallerySection
import com.moscow.cineverse.screen.cast_detials.components.ActorMainDetailsSection
import com.moscow.cineverse.screen.cast_detials.components.ActorMoviesSection
import com.moscow.cinverse.presentation.R

@Composable
fun CastDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CastDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToCastBestOfMovie: (Int, String) -> Unit,
    navigateToCastGallery: (Int, String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            CastDetailsEffectHandler.handleEffect(
                effect,
                context,
                navigateBack,
                navigateToMovieDetails,
                navigateToCastBestOfMovie,
                navigateToCastGallery
            )
        }
    }

    MovieScaffold {
        Column(modifier = modifier.fillMaxSize()) {
            MovieAppBar(
                title = "",
                backButtonClick = navigateBack,
                showBackButton = true,
                showAddButton = false,
                showLogo = false,
                showDivider = true
            )
            CastDetailsContent(
                modifier = Modifier.weight(1f), uiState = uiState, interactionListener = viewModel
            )
        }
    }
}

@Composable
private fun CastDetailsContent(
    modifier: Modifier = Modifier,
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
) {
    when {
        uiState.isLoading ->
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MovieCircularProgressBar(modifier)
            }

        uiState.shouldShowError -> ErrorContent(
            errorMessage = uiState.errorMessage,
            onRetry = interactionListener::onRefresh,
            modifier = modifier
        )

        uiState.isContentEmpty ->
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon = painterResource(R.drawable.outline_search),
                    title = stringResource(R.string.nothing_found),
                    description = stringResource(R.string.we_searched_the_entire_universe_but_found_nothing_matching_your_query_try_something_else)
                )
            }

        uiState.actor != null -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    ActorMainDetailsSection(
                        uiState,
                        interactionListener,
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    )
                }
                item {
                    ActorMoviesSection(
                        uiState,
                        interactionListener,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }
                item {
                    ActorGallerySection(
                        uiState,
                        interactionListener,
                        Modifier.padding(bottom = 24.dp)
                    )
                }
                item {
                    ActorBiographySection(uiState)
                }
            }
        }
    }
}