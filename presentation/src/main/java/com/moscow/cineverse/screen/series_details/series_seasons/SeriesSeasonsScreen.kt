package com.moscow.cineverse.screen.series_details.series_seasons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreenInteractionListener
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreenScreenViewModel
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreenState
import com.moscow.cineverse.screen.series_details.component.SeasonCard
import com.moscow.cinverse.presentation.R

@Composable
fun SeriesSeasonsScreen(
    modifier: Modifier = Modifier,
    viewModel: SeriesDetailsScreenScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SeriesSeasonsScreenContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier,
        onNavigateBack = navigateBack,
    )
}

@Composable
fun SeriesSeasonsScreenContent(
    uiState: SeriesDetailsScreenState,
    interactionListener: SeriesDetailsScreenInteractionListener,
    onNavigateBack : () -> Unit,
    modifier: Modifier = Modifier,
) {
    MovieScaffold {
        Box(modifier = modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    MovieCircularProgressBar(modifier = Modifier.align(Alignment.Center))
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
                            title = stringResource(R.string.seasons),
                            backButtonClick = onNavigateBack,
                        )
                        LazyColumn (
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(uiState.seriesDetail.seasons) { season ->
                                SeasonCard(
                                    seasonNumber = season.title,
                                    episodeCount = season.episodeCount,
                                    airDate = season.airDate,
                                    posterUrl = season.posterPath,
                                    caption = season.overview,
                                    rate = season.rate,
                                    enableBlur = uiState.enableBlur,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}