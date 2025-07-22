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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.ViewModeToggle
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreenState
import com.moscow.cineverse.screen.series_details.SeriesDetailsViewModel
import com.moscow.cineverse.screen.series_details.SeriesInteractionListener
import com.moscow.cinverse.presentation.R
import androidx.compose.runtime.getValue

@Composable
fun SeriesRecommendationScreen(
    viewModel: SeriesDetailsViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current,
    ) {
    val uiState by viewModel.uiState.collectAsState()

    SeriesRecommendationScreenContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier,
        onNavigateBack = {navController.popBackStack()},
    )
}

@Composable
fun SeriesRecommendationScreenContent(
    uiState: SeriesDetailsScreenState,
    interactionListener: SeriesInteractionListener,
    onNavigateBack : () -> Unit,
    modifier: Modifier = Modifier,
) {
    MovieScaffold {
        Box(modifier = modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    MovieCircularProgressBar(
                        modifier = Modifier.align(Alignment.Center),
                        gradientColors = listOf(
                            Theme.colors.brand.primary,
                            Theme.colors.brand.tertiary
                        )
                    )
                }
                uiState.errorMessage != "" -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MovieText(
                                text = uiState.errorMessage,
                                color = Theme.colors.shade.primary
                            )
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
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        MovieAppBar(
                            title = uiState.seriesDetail.title,
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
                            items(uiState.recommendation) { item ->
                                MoviePosterCard(
                                    movie = item,
                                    viewMode = uiState.viewMode,
                                    onMovieClick = {}
                                )
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