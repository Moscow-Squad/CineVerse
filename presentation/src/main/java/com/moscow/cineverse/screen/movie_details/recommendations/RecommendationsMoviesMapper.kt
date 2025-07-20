package com.moscow.cineverse.screen.movie_details.recommendations



import com.android.domain.model.Genre
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.moscow.cineverse.screen.explore.YYYY_MMM_DD
import com.moscow.cineverse.screen.explore.formatWith
import com.moscow.cineverse.common_ui_state.MediaItemUiState

fun List<Movie>.toUi(
    genresList: List<GenreUi>
): List<MediaItemUiState> {
    return this.map { movie ->
        MediaItemUiState(
            id = movie.id,
            title = movie.name,
            posterPath = movie.posterPath,
            rating = movie.rating,
            genres = movie.genreIds.map {genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate.formatWith(YYYY_MMM_DD) ?: "",
            duration = "",
            mediaType = MediaType.Movie
        )
    }
}
fun Genre.toUi() =
    GenreUi(
        id = id,
        name = name
    )

