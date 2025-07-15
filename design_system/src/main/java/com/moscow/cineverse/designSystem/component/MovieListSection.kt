package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MovieListSection(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<Movie>,
    onClickShowMore: () -> Unit = {},
    onClickPoster: (Movie) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SectionTitle(
            title = title,
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClickShowMore
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(168.dp)
        ) {
            itemsIndexed(movies) { _, movie ->
                MovieCard(
                    movieData = MockMovieData(
                        id = 1,
                        title = "The Dark Knight",
                        posterUrl = "",
                        rating = 9.0f,
                        genres = listOf("Action", "Crime", "Drama"),
                        duration = "2h 32m",
                        releaseDate = "2008, Jul 18"
                    ),
                    viewMode = ViewMode.GRID,
                    onMovieClick = { onClickPoster(movie) },
                    getId = { it.id },
                    getTitle = { it.title },
                    getPosterUrl = { it.posterUrl },
                    getRating = { it.rating },
                    getGenres = { it.genres },
                    getDuration = { it.duration },
                    getReleaseDate = { it.releaseDate },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(136.dp)

                )

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Movie List Section Preview")
@Composable
fun MovieListSectionPreview() {
    val dummyMovies = listOf(
        Movie(
            id = 1,
            title = "The Crimson Man",
            posterUrl = "",
            rating = 8.8f,
            genres = listOf("Action", "Sci-Fi"),
            duration = "2h 28m",
            releaseDate = "2010-07-16"
        ),
        Movie(
            id = 2,
            title = "Interstellar",
            posterUrl = "",
            rating = 9.9f,
            genres = listOf("Adventure", "Drama", "Sci-Fi"),
            duration = "2h 49m",
            releaseDate = "2014-11-07"
        ),
        Movie(
            id = 4,
            title = "PK",
            posterUrl = "",
            rating = 8.8f,
            genres = listOf("Action", "Sci-Fi"),
            duration = "2h 28m",
            releaseDate = "2010-07-16"
        ),
        Movie(
            id = 564,
            title = "The Matrix",
            posterUrl = "",
            rating = 8.8f,
            genres = listOf("Action", "Sci-Fi"),
            duration = "2h 28m",
            releaseDate = "2010-07-16"
        ),
        Movie(
            id = 5454567,
            title = "Toy Story",
            posterUrl = "",
            rating = 8.8f,
            genres = listOf("Action", "Sci-Fi"),
            duration = "2h 28m",
            releaseDate = "2010-07-16"
        ),
    )

    MaterialTheme {
        MovieListSection(
            title = "Matches your Vibe",
            movies = dummyMovies,
            onClickShowMore = {  },
            onClickPoster = { }
        )
    }
}