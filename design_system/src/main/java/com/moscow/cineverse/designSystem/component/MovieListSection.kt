package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*@Composable
fun MovieListSection(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<Movie>,
    paddingHorizontal: Int = 16,
    onClickShowMore: () -> Unit = {},
    onClickPoster: (Movie) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SectionTitle(
            title = title,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = paddingHorizontal.dp),
            onClick = onClickShowMore
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = paddingHorizontal.dp)
        ) {
            itemsIndexed(movies) { index, movie ->
                MoviePosterCard(
                    modifier = Modifier
                        .width(136.dp),
                    movie = movies[index],
                    viewMode = ViewMode.GRID,
                    onMovieClick = { movie -> onClickPoster(movie) }
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
*/