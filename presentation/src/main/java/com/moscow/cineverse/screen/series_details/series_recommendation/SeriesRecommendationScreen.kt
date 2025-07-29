package com.moscow.cineverse.screen.series_details.series_recommendation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.ViewModeToggle
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.mapper.toUi
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.Series

@Composable
fun SeriesRecommendationScreen(
    modifier: Modifier = Modifier,
    viewModel: SeriesRecommendationViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToSeriesDetails: (Int) -> Unit
    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recommendations = viewModel.getRecommendations(viewModel.seriesId).collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                is SeriesRecommendationEffects.NavigateToSeriesDetailsScreen -> {
                    navigateToSeriesDetails(event.seriesId)
                }
            }
        }
    }

    SeriesRecommendationScreenContent(
        title = viewModel.seriesName,
        uiState = uiState,
        recommendations = recommendations,
        interactionListener = viewModel,
        modifier = modifier,
        onNavigateBack = navigateBack
    )
}

@Composable
fun SeriesRecommendationScreenContent(
    title: String,
    uiState: SeriesRecommendationScreenState,
    recommendations: LazyPagingItems<Series>,
    interactionListener: SeriesRecommendationInteractionListener,
    onNavigateBack : () -> Unit,
    modifier: Modifier = Modifier,
) {
    MovieScaffold {
        Box(modifier = modifier.fillMaxSize()) {
            when (recommendations.loadState.append) {
                is LoadState.Loading -> {
                    MovieCircularProgressBar(modifier = Modifier.align(Alignment.Center))
                }
                is LoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            MovieButton(
                                buttonText = stringResource(R.string.retry),
                                textColor = Theme.colors.button.primary,
                                textStyle = Theme.textStyle.title.small,
                                onClick = {}
                            )
                        }
                    }
                }
                is LoadState.NotLoading -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        MovieAppBar(
                            caption = stringResource(R.string.because_you_watched),
                            title = title,
                            backButtonClick = onNavigateBack,
                        )
                        LazyVerticalGrid(
                            columns = if (uiState.viewMode == ViewMode.GRID)
                                GridCells.Fixed(2)
                            else
                                GridCells.Fixed(1),
                            contentPadding = PaddingValues(
                                vertical = 16.dp,
                                horizontal = 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(recommendations.itemCount) { index ->
                                val recommendation = recommendations[index]?.toUi()
                                if (recommendation != null) {
                                    MoviePosterCard(
                                        movie = recommendation,
                                        viewMode = uiState.viewMode,
                                        onMovieClick = {
                                            interactionListener.onSeriesClicked(
                                                recommendation.id
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                    ViewModeToggle(
                        selectedMode = uiState.viewMode,
                        onModeSelected = interactionListener::onViewModeChanged,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}