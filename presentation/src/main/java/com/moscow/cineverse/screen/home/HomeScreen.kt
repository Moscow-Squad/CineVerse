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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.component.ScreenStateHandler
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.LocalScaffoldPaddingValues
import com.moscow.cineverse.screen.home.components.FeaturedMovies
import com.moscow.cineverse.screen.home.components.HomeHeader
import com.moscow.cineverse.screen.home.components.HomeHeaderSlider
import com.moscow.cineverse.screen.home.components.MyCollectionsLayout
import com.moscow.cineverse.screen.home.components.SuggestionWithHeader
import com.moscow.cinverse.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: HomeViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current,
) {
    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewmodel.uiEffect.collect { effect ->
            when (effect) {
                is HomeEvent.CollectionClicked -> {}
                is HomeEvent.MovieClicked -> {}
                is HomeEvent.PromotionClicked -> {}
                is HomeEvent.SeeAllClicked -> {
                    // Navigate to SeeMoreHomeScreen with the category
                    navController.navigate("see_more/${effect.category.name}")
                }

                is HomeEvent.SeriesClicked -> {}
            }
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
                .padding(LocalScaffoldPaddingValues.current),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HomeHeader(userName = state.userName, modifier)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Theme.colors.stroke.primary),
                )

            }

            HomeHeaderSlider(items = state.sliderItems)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                FeaturedMovies(
                    displayMovies = state.recentlyReleasedMovies,
                    onMovieClick = listener::onMovieClick,
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
                    onMovieClick = listener::onMovieClick,
                    onShowMoreClick = listener::onSeeAllClick,
                    type = HomeFeaturedItems.UPCOMING_MOVIES,
                    modifier = Modifier,
                )

                FeaturedMovies(
                    displayMovies = state.matchesYourVibe,
                    onMovieClick = listener::onMovieClick,
                    onShowMoreClick = listener::onSeeAllClick,
                    modifier = Modifier,
                    type = HomeFeaturedItems.MATCHES_YOUR_VIBE
                )

                FeaturedMovies(
                    displayMovies = state.topRatedTvShows,
                    onMovieClick = listener::onMovieClick,
                    onShowMoreClick = listener::onSeeAllClick,
                    modifier = Modifier,
                    type = HomeFeaturedItems.TOP_RATED_TV_SHOWS
                )

                FeaturedMovies(
                    displayMovies = state.matchesYourVibe,
                    onMovieClick = listener::onMovieClick,
                    onShowMoreClick = listener::onSeeAllClick,
                    modifier = Modifier,
                    type = HomeFeaturedItems.YOU_RECENTLY_VIEWED
                )

                MyCollectionsLayout(
                    items = state.collections,
                    onCollectionClick = listener::onCollectionClick,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onShowMoreClick = listener::onCollectionsShowMoreClick,
                )

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