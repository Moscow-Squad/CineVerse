package com.moscow.cineverse.screen.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.explore.component.ExploreMainContent
import com.moscow.cineverse.screen.explore.component.ExploreTopBar
import com.moscow.cineverse.screen.explore.component.SearchSuggestionsSection
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
    navigateToCastDetails: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val contentList = viewModel.contentList.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { event ->
            handleEffects(
                event,
                navigateToCastDetails = navigateToCastDetails,
                navigateToMovieDetails = navigateToMovieDetails,
                navigateToSeriesDetails = navigateToSeriesDetails
            )
        }
    }

    ExploreScreenContent(
        uiState = uiState,
        contentList = contentList,
        interactionListener = viewModel,
        modifier = modifier
    )
}

private fun handleEffects(
    event: ExploreScreenEffects,
    navigateToCastDetails: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
) {
    when (event) {
        is ExploreScreenEffects.ActorClicked -> {
            navigateToCastDetails(event.actorId)
        }

        is ExploreScreenEffects.GenreSelected -> {}
        ExploreScreenEffects.LoadData -> {}
        is ExploreScreenEffects.MovieClicked -> {
            navigateToMovieDetails(event.movieId)
        }

        ExploreScreenEffects.RefreshRequested -> {}
        is ExploreScreenEffects.TabSelected -> {}
        is ExploreScreenEffects.ViewModeChanged -> {}
        is ExploreScreenEffects.SeriesClicked -> {
            navigateToSeriesDetails(event.seriesId)
        }
    }
}

@Composable
private fun ExploreScreenContent(
    uiState: ExploreScreenState,
    contentList: androidx.paging.compose.LazyPagingItems<Any>,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    val genresState = rememberLazyListState()

    var searchBarVisible by remember { mutableStateOf(true) }
    var genresVisible by remember { mutableStateOf(true) }

    LaunchedEffect(uiState.selectedTab) {
        gridState.animateScrollToItem(0)
        val genreId = if (uiState.selectedTab == ExploreTabsPages.MOVIES) {
            uiState.selectedMovieGenre
        } else {
            uiState.selectedSeriesGenre
        }
        val targetIndex = uiState.genres.indexOfFirst { it.id == genreId }
        if (targetIndex >= 0) genresState.animateScrollToItem(targetIndex)
        searchBarVisible = true
        genresVisible = true
    }

    LaunchedEffect(uiState.shouldShowGenres) {
        if (uiState.shouldShowGenres) {
            searchBarVisible = true
            genresVisible = true
        }
    }

    MovieScaffold(
        modifier = modifier.fillMaxSize(),
        movieAppBar = {
            ExploreTopBar(
                uiState = uiState,
                genresState = genresState,
                interactionListener = interactionListener,
                searchBarVisible = searchBarVisible,
                genresVisible = genresVisible
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                if (!uiState.showSuggestions) {
                    ExploreMainContent(
                        uiState = uiState,
                        gridState = gridState,
                        contentList = contentList,
                        interactionListener = interactionListener,
                        onVisibilityChange = { searchVisible, genresVisibleState ->
                            searchBarVisible = searchVisible
                            genresVisible = genresVisibleState
                        }
                    )
                }

                SearchSuggestionsSection(
                    uiState = uiState,
                    interactionListener = interactionListener,
                    modifier = Modifier.fillMaxSize()
                )

                if (uiState.selectedTab != ExploreTabsPages.ACTORS && !uiState.showSuggestions) {
                    ViewModeToggleButton(
                        selectedMode = uiState.viewMode,
                        onModeSelected = interactionListener::onViewModeChanged,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 96.dp)
                    )
                }
            }
        }
    )
}