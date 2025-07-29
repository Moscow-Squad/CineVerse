package com.moscow.cineverse.screen.movie_details.recommendations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.Movie

@Composable
fun RecommendationMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: RecommendationsMoviesViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,

    ) {
    val recommendations = viewModel.getRecommendations().collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when(event){
                RecommendationMoviesEffect.NavigateBack -> {
                    navigateBack()
                }

                is RecommendationMoviesEffect.MovieClicked -> {
                    navigateToMovieDetails(
                        event.movieId,
                    )
                }
            }
        }
    }
    RecommendationMoviesContent(
        uiState,
        recommendations = recommendations,
        interactionListener = viewModel,
        modifier = modifier,
        title = uiState.movieTitle
    )
}


@Composable
fun RecommendationMoviesContent(
    uiState: RecommendationsMoviesState,
    recommendations: LazyPagingItems<Movie>,
    interactionListener: RecommendationsMoviesInteractionListener,
    modifier: Modifier = Modifier,
    title: String
) {
    MovieScaffold {
        Box(modifier = modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                MovieAppBar(
                    caption = stringResource(R.string.because_you_watched),
                    title = title,
                    backButtonClick = interactionListener::backButtonClick,
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
                    items(recommendations.itemCount)
                    { index ->
                        val recommendation = recommendations[index]?.toMediaItemUi()
                        if (recommendation != null) {
                            MoviePosterCard(
                                movie = recommendation,
                                viewMode = uiState.viewMode,
                                onMovieClick = interactionListener::onMovieClick
                            )


                        }
                    }

                    when (recommendations.loadState.append) {
                        is androidx.paging.LoadState.Loading -> {
                            item {
                                Text(
                                    "Loading more...",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        is androidx.paging.LoadState.Error -> {
                            item {
                                Text(
                                    "Error loading more data.",
                                    modifier = Modifier.padding(16.dp),

                                    )
                            }
                        }

                        else -> {}
                    }
                }

            }
            ViewModeToggleButton(
                selectedMode = uiState.viewMode,
                onModeSelected = interactionListener::onViewModeChanged,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}