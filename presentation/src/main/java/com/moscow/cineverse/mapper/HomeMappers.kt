package com.moscow.cineverse.mapper


import com.moscow.cineverse.common_ui_state.CollectionUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.YYYY_MMM_DD
import com.moscow.cineverse.screen.explore.formatWith
import com.moscow.cineverse.screen.home.HomeUiState
import com.moscow.domain.model.Collection
import com.moscow.domain.model.Genre
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import kotlin.Boolean

fun Genre.toGenreUi() = HomeUiState.GenreUi(
    id = id,
    name = name
)

fun List<Movie>.toUi(
    genresList: List<HomeUiState.GenreUi>
): List<MediaItemUiState> {
    return this.map { movie ->
        MediaItemUiState(
            id = movie.id,
            title = movie.name,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            rating = movie.rating,
            genres = movie.genreIds.map { genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate.formatWith(YYYY_MMM_DD) ?: "",
            duration = "",
            mediaType = MediaType.Movie
        )
    }
}
fun List<Series>.toUi(
): List<MediaItemUiState> {
    return this.map { series ->
        MediaItemUiState(
            id = series.id,
            title = series.name,
            posterPath = series.posterPath,
            rating = series.rating,
            genres = emptyList(),
            releaseDate = series.firstAirDate.toString(),
            duration = "",
            backdropPath = series.backdropPath,
            mediaType = MediaType.Tv

        )
    }
}

fun Collection.toUi() = CollectionUiState(
    id = id,
    title = name,
    numberOfShows = numberOfItems,
    isLoading = false
)