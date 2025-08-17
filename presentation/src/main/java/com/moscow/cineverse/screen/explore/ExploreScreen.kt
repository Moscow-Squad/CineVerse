package com.moscow.cineverse.screen.explore

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.screen.explore.component.ExploreMainContent
import com.moscow.cineverse.screen.explore.component.ExploreSearchBarSection
import com.moscow.cineverse.screen.explore.component.ExploreTabsSection
import com.moscow.cineverse.screen.explore.component.GenresRow
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
        viewModel.uiEffect.collect { effect ->
            ExploreScreenEffectHandler.handleEffect (
                effect,
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ExploreScreenContent(
    uiState: ExploreScreenState,
    contentList: LazyPagingItems<Any>,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    val genresState = rememberLazyListState()

    var searchBarVisible by rememberSaveable { mutableStateOf(true) }
    var genresVisible by rememberSaveable { mutableStateOf(true) }

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
            genresVisible = true
            searchBarVisible = true
        }
    }
    MovieScaffold(
        modifier = modifier,
        movieAppBar = {
            Column {
                ExploreSearchBarSection(
                    uiState,
                    interactionListener,
                    isVisible = searchBarVisible
                )
                AnimatedVisibility(
                    visible = !uiState.showSuggestions,
                    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
                ){
                    ExploreTabsSection(
                        selectedTab = uiState.selectedTab,
                        onTabSelected = interactionListener::onTabSelected,
                        showAllTabs = uiState.isSearch
                    )
                }
            }
        }
    )
    {
        Box(modifier = Modifier.fillMaxSize()) {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = uiState.viewMode,
                    transitionSpec = {
                        fadeIn(tween(600)) togetherWith fadeOut(tween(600))
                    },
                    label = "view_mode_transition"
                ) { currentViewMode ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f)) {
                            when (contentList.loadState.refresh) {
                                is LoadState.Loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        MovieCircularProgressBar()
                                    }
                                }

                                is LoadState.Error -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        NoInternetScreen(onRetry = interactionListener::onRefresh)
                                    }
                                }

                                else -> {
                                    ExploreMainContent(
                                        uiState = uiState.copy(viewMode = currentViewMode),
                                        gridState = gridState,
                                        contentList = contentList,
                                        interactionListener = interactionListener,
                                        onGenresVisibilityChange = { shouldShow ->
                                            genresVisible = shouldShow
                                            searchBarVisible =
                                                if (uiState.searchKeyWord.isNotEmpty()) true else shouldShow
                                        },
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (uiState.genres.isNotEmpty()) {
                AnimatedVisibility(
                    visible = !uiState.showSuggestions,
                    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
                ){
                    GenresRow(
                        uiState = uiState,
                        genresState = genresState,
                        interactionListener = interactionListener,
                        isVisible = genresVisible,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
            if (uiState.selectedTab != ExploreTabsPages.ACTORS && uiState.shouldShowError == false) {
                ViewModeToggleButton(
                    selectedMode = uiState.viewMode,
                    onModeSelected = interactionListener::onViewModeChanged,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 96.dp)
                )
            }
            SearchSuggestionsSection(uiState, interactionListener)
        }
    }
}