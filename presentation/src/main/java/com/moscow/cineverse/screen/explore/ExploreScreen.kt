package com.moscow.cineverse.screen.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.screen.explore.component.ExploreMainContent
import com.moscow.cineverse.screen.explore.component.ExploreSearchBarSection
import com.moscow.cineverse.screen.explore.component.ExploreTabsSection
import com.moscow.cineverse.screen.explore.component.GenresRow
import com.moscow.cineverse.screen.explore.component.SearchSuggestionsSection
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current,
    viewModel: ExploreViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event -> handleEffects(event, navController) }
    }

    ExploreScreenContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier
    )
}

private fun handleEffects(
    event: ExploreScreenEffects,
    navController: NavHostController
) {
    when (event) {
        is ExploreScreenEffects.ActorClicked -> {
            navController.navigate(
                CastDetailsRoute(event.actorId)
            )
        }

        is ExploreScreenEffects.GenreSelected -> {}
        ExploreScreenEffects.LoadData -> {}
        is ExploreScreenEffects.MovieClicked -> {
            navController.navigate(
                MovieDetailsRoute(event.movieId)
            )
        }

        ExploreScreenEffects.RefreshRequested -> {}
        is ExploreScreenEffects.TabSelected -> {}
        is ExploreScreenEffects.ViewModeChanged -> {}
        is ExploreScreenEffects.SeriesClicked -> {
            navController.navigate(
                SeriesDetailsRoute(event.seriesId)
            )
        }
    }
}

@Composable
private fun ExploreScreenContent(
    uiState: ExploreScreenState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    val genresState = rememberLazyListState()

    LaunchedEffect(uiState.selectedTab) {
        gridState.animateScrollToItem(0)
        val genreId = if (uiState.selectedTab == ExploreTabsPages.MOVIES)
            uiState.selectedMovieGenre else uiState.selectedSeriesGenre
        val targetIndex = uiState.genres.indexOfFirst { it.id == genreId }
        if (targetIndex >= 0) genresState.animateScrollToItem(targetIndex)
    }

    Surface(modifier = modifier.fillMaxSize(), color = Theme.colors.background.screen) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ExploreSearchBarSection(uiState, interactionListener)
                ExploreTabsSection(uiState.selectedTab, interactionListener::onTabSelected, uiState.searchKeyWord.isNotEmpty())
                Box(modifier = Modifier.fillMaxSize()) {
                    ExploreMainContent(uiState, gridState, interactionListener)
                    GenresRow(uiState, genresState, interactionListener, modifier = Modifier.align(Alignment.TopCenter))
                }
            }
            SearchSuggestionsSection(uiState, interactionListener)
            if (uiState.selectedTab != ExploreTabsPages.ACTORS) {
                ViewModeToggleButton(
                    selectedMode = uiState.viewMode,
                    onModeSelected = interactionListener::onViewModeChanged,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                )
            }
        }
    }
}

