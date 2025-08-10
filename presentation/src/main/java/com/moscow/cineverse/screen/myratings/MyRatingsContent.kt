package com.moscow.cineverse.screen.myratings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.EmptyState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.component.ExploreTabsSection
import com.moscow.cineverse.screen.movieSeriesDetails.MovieRatingBar
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@Composable
fun MyRatingsContent(
    modifier: Modifier = Modifier,
    interactionListener: MyRatingsInteractionListener,
    uiState: MyRatingsUiState,
    contentList: LazyPagingItems<RatedMediaItem>
) {
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.selectedTab) {
        listState.animateScrollToItem(0)
    }

    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                title = stringResource(R.string.my_ratings),
                showDivider = true,
                showBackButton = true,
                backButtonClick = { interactionListener.onNavigateBack() }
            )
        },
        modifier = modifier.background(Theme.colors.background.screen)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ExploreTabsSection(
                selectedTab = uiState.selectedTab,
                onTabSelected = interactionListener::onTabSelected,
                showAllTabs = false
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    contentList.loadState.refresh is LoadState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieCircularProgressBar()
                        }
                    }

                    contentList.loadState.refresh is LoadState.Error -> {
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
                                icon = painterResource(R.drawable.due_tone_star),
                                title = stringResource(R.string.no_ratings_yet),
                                description = stringResource(R.string.rate_movies_and_series_you_ve_watched_to_track_what_you_love_and_what_you_don_t),
                                buttonTitle = stringResource(R.string.start_rating),
                                showButton = true,
                                onButtonClick = interactionListener::onEmptyStateButtonClicked
                            )
                        }
                    }

                    else -> {
                        MyRatingsList(
                            contentList = contentList,
                            onMediaItemClicked = interactionListener::onMediaItemClicked,
                            modifier = Modifier.fillMaxSize(),
                            enableBlur = uiState.enableBlur
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MyRatingsList(
    contentList: LazyPagingItems<RatedMediaItem>,
    onMediaItemClicked: (MediaItemUiState) -> Unit,
    modifier: Modifier = Modifier,
    enableBlur: String
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(contentList.itemCount) { index ->
            contentList[index]?.let { mediaItem ->
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(R.string.you_gave_it),
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.primary
                        )
                        MovieRatingBar(
                            mediaItem.rating,
                            onRatingChanged = {},
                        )
                    }

                    MoviePosterCard(
                        movie = mediaItem.mediaItem,
                        onMovieClick = { onMediaItemClicked(mediaItem.mediaItem) },
                        viewMode = ViewMode.LIST,
                        enableBlur = enableBlur
                    )
                }
            }
        }
        if (contentList.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier.height(214.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MovieCircularProgressBar()
                }
            }
        }
        if (contentList.loadState.append is LoadState.Error) {
            item {
                NoInternetScreen(onRetry = { contentList.retry() })
            }
        }
    }
}