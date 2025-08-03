package com.moscow.cineverse.screen.collection_details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.message_info.InfoCard
import com.moscow.cineverse.screen.MovieCard
import com.moscow.cineverse.screen.collections.composable.SwipeToDeleteItem
import com.moscow.cineverse.utlis.ViewMode

@Composable
fun CollectionDetailsScreen(
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit
){
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                modifier = Modifier.padding(top = 40.dp),
                backButtonClick = {},
                title = "Action Movies",
            )
        }
    ){
        LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)){
            item {
                InfoCard(text = "Tip: Swipe left to remove movies from your collection.")
            }
            items(fakeMovies){ media ->
                SwipeToDeleteItem(
                    onDelete = {}
                ) {
                    MovieCard(
                        movieData = media,
                        viewMode = ViewMode.LIST,
                        showRating = true,
                        onMovieClick = {},
                        showTitle = true,
                        showBackdrop = true,
                        getId = {media.id},
                        getTitle = { media.title },
                        getPosterUrl = { media.posterPath },
                        getBackdropUrl = { media.backdropPath },
                        getRating = { media.rating },
                        getGenres = { media.genres },
                        getDuration = { media.duration },
                        getReleaseDate = { media.releaseDate }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CollectionDetailsScreenPreview(){
    CollectionDetailsScreen(
        navigateBack = {},
        navigateToMovieDetails = {},
        navigateToSeriesDetails = {}
    )
}