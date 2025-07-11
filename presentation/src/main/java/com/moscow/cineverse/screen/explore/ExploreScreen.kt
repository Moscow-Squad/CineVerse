package com.moscow.cineverse.screen.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.PillLabel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.ViewModeToggle
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabs
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.presentation.component.MoviePosterCard

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    ExploreScreenContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier
    )
}

@Composable
private fun ExploreScreenContent(
    uiState: ExploreScreenState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Theme.colors.background.screen
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ExploreTabs(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = interactionListener::onTabSelected,
                    showAllTabs = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        uiState.shouldShowLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        uiState.shouldShowError -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = uiState.errorMessage,
                                        color = Theme.colors.shade.primary
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = interactionListener::onRefresh
                                    ) {
                                        Text("Retry")
                                    }
                                }
                            }
                        }

                        uiState.isContentEmpty -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No content available",
                                    color = Theme.colors.shade.primary
                                )
                            }
                        }

                        else -> {
                            LazyVerticalGrid(
                                columns = if (uiState.viewMode == ViewMode.GRID)
                                    GridCells.Adaptive(minSize = 160.dp)
                                else
                                    GridCells.Fixed(1),
                                contentPadding = PaddingValues(
                                    top = 56.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 100.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(uiState.contentList) { item ->
                                    val movie = item as UiMovie
                                    MoviePosterCard(
                                        movie = movie,
                                        viewMode = uiState.viewMode,
                                        onMovieClick = interactionListener::onMovieClick
                                    )
                                }
                            }
                        }
                    }

                    if (uiState.shouldShowGenres) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .align(Alignment.TopCenter)
                        ) {
                            items(uiState.genres) { genre ->
                                PillLabel(
                                    text = genre.name,
                                    isActive = uiState.selectedGenre?.id == genre.id,
                                    onClick = { interactionListener.onGenreSelected(genre) }
                                )
                            }
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
