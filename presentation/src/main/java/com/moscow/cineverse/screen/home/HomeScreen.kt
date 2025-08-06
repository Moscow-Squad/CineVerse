package com.moscow.cineverse.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ScreenStateHandler
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalScaffoldPaddingValues
import com.moscow.cineverse.screen.home.components.FeaturedCollectionsSection
import com.moscow.cineverse.screen.home.components.FeaturedMovies
import com.moscow.cineverse.screen.home.components.HomeHeader
import com.moscow.cineverse.screen.home.components.HomeHeaderSlider
import com.moscow.cineverse.screen.home.components.MyCollectionsLayout
import com.moscow.cineverse.screen.home.components.SuggestionWithHeader
import com.moscow.cinverse.presentation.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: HomeViewModel = hiltViewModel(),
    navigateToMovieDetails: (movieId: Int) -> Unit,
    navigateToSeeMoreHome: (category: String) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,
    navigateToBrowseSuggestion: () -> Unit,
    navigateToWatchingSuggestion: () -> Unit,
    navigateToCollectionDetails: (collectionId: Int, collectionName: String) -> Unit,
) {
    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewmodel.uiEffect.collect { effect ->
            when (effect) {
                is HomeEvent.CollectionClicked -> {
                    navigateToCollectionDetails(
                        effect.collectionId,
                        effect.collectionName
                    )
                }

                is HomeEvent.MovieClicked -> {
                    navigateToMovieDetails(
                        effect.movieId
                    )
                    viewmodel.getRecentlyViewedMovies()
                }

                is HomeEvent.PromotionClicked -> {}
                is HomeEvent.SeeAllClicked -> {
                    navigateToSeeMoreHome(
                        effect.category.name
                    )
                    // Navigate to SeeMoreHomeScreen with the category
                }

                is HomeEvent.SeriesClicked -> {
                    navigateToSeriesDetails(
                        effect.seriesId
                    )
                }

                is HomeEvent.BrowseSuggestionClicked -> {
                    // Use the same navigation method as bottom navigation
                    navigateToBrowseSuggestion()
                }

                HomeEvent.WatchingSuggestionClicked -> {
                    navigateToWatchingSuggestion()
                }
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewmodel.getRecentlyViewedMovies()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    HomeContent(modifier, state, viewmodel)
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    listener: HomeInteractionListener
) {

    ScreenStateHandler(
        isLoading = state.isLoading,
        errorMessage = state.error,
        onRefresh = listener::onRefresh,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(LocalScaffoldPaddingValues.current)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HomeHeader(userName = state.userName ?: stringResource(R.string.guest), modifier)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Theme.colors.stroke.primary),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {

                HomeHeaderSlider(
                    items = state.sliderItems,
                    onSliderClick = listener::onMediaItemClicked,
                    modifier = Modifier.padding(top = 16.dp)
                )

                FeaturedMovies(
                    displayMovies = state.recentlyReleasedMovies,
                    onMovieClick = listener::onMediaItemClicked,
                    onShowMoreClick = listener::onSeeAllClick,
                    type = HomeFeaturedItems.RECENTLY_RELEASED,
                    modifier = Modifier
                )

                SuggestionWithHeader(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    header = stringResource(id = R.string.what_sould_i_watch),
                    title = stringResource(id = R.string.let_us_choose_for_you),
                    message = stringResource(id = R.string.let_us_choose_message),
                    onClick = listener::onWatchSuggestionClick,
                )

                FeaturedMovies(
                    displayMovies = state.upcomingMovies,
                    onMovieClick = listener::onMediaItemClicked,
                    onShowMoreClick = listener::onSeeAllClick,
                    type = HomeFeaturedItems.UPCOMING_MOVIES,
                    modifier = Modifier,
                )

                FeaturedMovies(
                    displayMovies = state.matchesYourVibe,
                    onMovieClick = listener::onMediaItemClicked,
                    onShowMoreClick = listener::onSeeAllClick,
                    modifier = Modifier,
                    type = HomeFeaturedItems.MATCHES_YOUR_VIBE
                )

                FeaturedCollectionsSection(
                    collections = HomeFeaturedCollections.entries.toList(),
                    onCollectionClick = {},
                )

                FeaturedMovies(
                    displayMovies = state.topRatedTvShows,
                    onMovieClick = listener::onMediaItemClicked,
                    onShowMoreClick = listener::onSeeAllClick,
                    modifier = Modifier,
                    type = HomeFeaturedItems.TOP_RATED_TV_SHOWS
                )

                if (!state.youRecentlyViewed.isEmpty()) {
                    FeaturedMovies(
                        displayMovies = state.youRecentlyViewed,
                        onMovieClick = listener::onMediaItemClicked,
                        onShowMoreClick = listener::onSeeAllClick,
                        modifier = Modifier,
                        type = HomeFeaturedItems.YOU_RECENTLY_VIEWED
                    )
                }

                if (state.userName != null) {
                    MyCollectionsLayout(
                        items = state.collections.take(4),
                        onCollectionClick = listener::onCollectionClick,
                        onShowMoreClick = listener::onCollectionsShowMoreClick,
                    )
                }

                SuggestionWithHeader(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    header = stringResource(id = R.string.need_more_to_watch),
                    title = stringResource(id = R.string.browse_everything),
                    message = stringResource(id = R.string.browse_everything_message),
                    onClick = listener::onBrowseSuggestionClick,
                )

            }
        }
    }

}