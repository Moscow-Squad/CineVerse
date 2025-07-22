package com.moscow.cineverse.screen.explore

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.designSystem.component.EmptyState
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.PillLabel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.ViewModeToggle
import com.moscow.cineverse.designSystem.component.search.SearchBar
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabs
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.screen.explore.component.ActorPosterCard
import com.moscow.cineverse.screen.explore.component.SearchSuggestion
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.component.VoiceRecognitionIcon
import com.moscow.cinverse.presentation.R
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
    val context = LocalContext.current
    val gridState = rememberLazyGridState()
    val genresState = rememberLazyListState()

    LaunchedEffect(uiState.selectedTab) {
        gridState.animateScrollToItem(0)
        val genreIdToFind = if (uiState.selectedTab == ExploreTabsPages.MOVIES)
            uiState.selectedMovieGenre
        else
            uiState.selectedSeriesGenre

        val targetIndex = uiState.genres.indexOfFirst { it.id == genreIdToFind }
        if (targetIndex >= 0) {
            genresState.animateScrollToItem(targetIndex)
        }
    }


    Surface(
        modifier = modifier.fillMaxSize(),
        color = Theme.colors.background.screen
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp)
                        .padding(horizontal = 16.dp),
                    value = uiState.searchKeyWord,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            interactionListener.onKeyboardClick()
                        },
                        onSearch = {
                            if (uiState.searchKeyWord.isBlank())
                                Toast.makeText(
                                    context,
                                    "Search can't be empty!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            else
                                interactionListener.onSearchQuery()
                        }
                    ),
                    onValueChange = { interactionListener.onSearchValueChange(it) },
                    onCancelButtonClicked = { interactionListener.onCancelButtonClicked() },
                    onFirstFocus = { interactionListener.onSearchBarClickedOn() },
                    trailingIcon = {
                        VoiceRecognitionIcon(
                            modifier = Modifier.size(20.dp),
                            onResult = { interactionListener.onSearchWordDetected(it) },
                            onError = {}
                        )
                    }
                )
                ExploreTabs(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = interactionListener::onTabSelected,
                    showAllTabs = uiState.searchKeyWord.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        uiState.isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                MovieCircularProgressBar(
                                    gradientColors = listOf(
                                        Theme.colors.brand.primary,
                                        Theme.colors.brand.tertiary
                                    )
                                )
                            }
                        }

                        uiState.shouldShowError -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                NoInternetScreen(onRetry = interactionListener::onRefresh)
                            }
                        }

                        uiState.isContentEmpty -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptyState(
                                icon = painterResource(Theme.icons.dueTone.search),
                                title = stringResource(R.string.nothing_found),
                                description = stringResource(R.string.we_searched_the_entire_universe_but_found_nothing_matching_your_query_try_something_else)
                                )
                            }
                        }

                        else -> {
                            val gridColumns = remember(uiState.viewMode, uiState.selectedTab) {
                                if (uiState.viewMode == ViewMode.GRID) {
                                    when (uiState.selectedTab) {
                                        ExploreTabsPages.ACTORS -> GridCells.Adaptive(minSize = 98.dp) // Smaller for actors
                                        ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES -> GridCells.Adaptive(
                                            minSize = 160.dp
                                        ) // Larger for movies/series
                                    }
                                } else {
                                    GridCells.Fixed(1)
                                }
                            }
                            LazyVerticalGrid(
                                state = gridState,
                                columns = gridColumns,
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
                                    when (item) {
                                        is MediaItemUiState -> {
                                            MoviePosterCard(
                                                movie = item,
                                                viewMode = uiState.viewMode,
                                                onMovieClick = {
                                                    interactionListener.onMediaItemClicked(
                                                        item
                                                    )
                                                }
                                            )
                                        }
                                        is ExploreScreenState.ActorUiState -> {
                                            ActorPosterCard(
                                                actor = item,
                                                viewMode = uiState.viewMode,
                                                onActorClicked = interactionListener::onActorClick
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (uiState.shouldShowGenres) {
                        LazyRow(
                            state = genresState,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                                .padding(top = 12.dp)
                        ) {
                            val selectedGenre =
                                if (uiState.selectedTab == ExploreTabsPages.MOVIES) uiState.selectedMovieGenre else uiState.selectedSeriesGenre
                            items(uiState.genres) { genre ->
                                PillLabel(
                                    text = genre.name,
                                    isActive = selectedGenre == genre.id,
                                    onClick = {
                                        if (uiState.selectedTab == ExploreTabsPages.SERIES)
                                            interactionListener.onSeriesGenreSelected(genre.id)
                                        else
                                            interactionListener.onMovieGenreSelected(genre.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = uiState.showSuggestions,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500)
                ) + fadeIn(animationSpec = tween(500)),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            ) {
                SearchSuggestion(
                    modifier = Modifier.padding(top = 24.dp),
                    suggestionList = uiState.displayedSuggestions,
                    isHistory = uiState.showHistory,
                    onClickSuggestion = interactionListener::onClickSuggestion,
                    onClearAllClicked = interactionListener::clearAllLocalSuggestions
                )
            }

            if (uiState.selectedTab != ExploreTabsPages.ACTORS)
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
