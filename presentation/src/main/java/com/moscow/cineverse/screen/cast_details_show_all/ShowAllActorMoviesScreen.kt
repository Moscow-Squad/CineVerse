package com.moscow.cineverse.screen.cast_details_show_all

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.CineVersePreviews
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.MovieText
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.ViewModeToggle
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.component.movie_poster_card.MediaItemUi
import com.moscow.cineverse.screen.component.movie_poster_card.MoviePosterCard
import com.moscow.cinverse.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowAllActorMoviesScreen(
    modifier: Modifier = Modifier,
    title: String
) {
    val viewModel: ShowAllActorMoviesInteractionViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    ShowAllActorMoviesContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier,
        title = title
    )
}


@Composable
fun ShowAllActorMoviesContent(
    uiState: ShowAllActorMoviesState,
    interactionListener: ShowAllActorMoviesInteractionListener,
    modifier: Modifier = Modifier,
    title: String
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

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MovieText(
                                text = uiState.error,
                                color = Theme.colors.shade.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            MovieButton(
                                buttonText = stringResource(R.string.retry),
                                textColor = Theme.colors.button.primary,
                                textStyle = Theme.textStyle.title.small,
                                onClick = interactionListener::onRefresh
                            )
                        }
                    }
                }

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        MovieAppBar(
                            title = title,
                            backButtonClick = interactionListener::backButtonClick,
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
                            items(uiState.movies) { item ->
                                MoviePosterCard(
                                    movie = item,
                                    viewMode = uiState.viewMode,
                                    onMovieClick = interactionListener::onMovieClick
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

@CineVersePreviews
@Composable
fun ShowAllActorMoviesPreview(modifier: Modifier = Modifier) {
    ShowAllActorMoviesContent(
        uiState = ShowAllActorMoviesState(
            isLoading = false,
            error = null,
            viewMode = ViewMode.GRID,
            movies = List(20) { index ->
                MediaItemUi(
                    id = index,
                    title = "Movie $index",
                    posterPath = "https://example.com/poster_$index.jpg",
                    rating = 7.5f,
                    genres = listOf("Action", "Adventure"),
                    duration = "2h 30m",
                    releaseDate = "2023-10-01"
                )
            }
        ),
        interactionListener = object : ShowAllActorMoviesInteractionListener {
            override fun onRefresh() {}
            override fun onViewModeChanged(viewMode: ViewMode) {}
            override fun onMovieClick(movieId: Int) {}
            override fun backButtonClick() {}
        },
        modifier = modifier,
        "sad"
    )
}
