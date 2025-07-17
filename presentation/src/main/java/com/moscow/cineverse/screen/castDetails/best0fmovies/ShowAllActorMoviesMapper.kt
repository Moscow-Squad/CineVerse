package com.moscow.cineverse.screen.castDetails.best0fmovies



import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.moscow.cineverse.screen.explore.YYYY_MMM_DD
import com.moscow.cineverse.screen.explore.formatWith
import com.moscow.cineverse.screen.model.MediaItemUi

fun List<Movie>.toUi(
    genresList: List<GenreUi>
): List<MediaItemUi> {
    return this.map { movie ->
        MediaItemUi(
            id = movie.id,
            title = movie.name,
            posterPath = movie.posterPath,
            rating = movie.rating,
            genres = movie.genreIds.map {genresList.first { genre -> genre.id == it }.name },
            releaseDate = movie.releaseDate.formatWith(YYYY_MMM_DD) ?: "",
            duration = ""
        )
    }
}
fun Genre.toUi() =
    GenreUi(
        id = id,
        name = name
    )

