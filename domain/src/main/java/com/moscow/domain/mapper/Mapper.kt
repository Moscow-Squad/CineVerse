package com.moscow.domain.mapper

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.model.details.MovieDetail
import com.moscow.domain.model.details.SeriesDetail


fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        name = title,
        genreIds = emptyList(),
        rating = voteAverage.toFloat(),
        releaseDate = releaseDate,
        adult = false,
        backdropPath = posterPath,
        originalLanguage = "en",
        originalTitle = title,
        overview = overview,
        posterPath = posterPath,
        video = false,
        poster = posterPath
    )
}

fun SeriesDetail.toSeries(): Series {
    return Series(
        id = id,
        name = this.title,
        genreIds = emptyList(),
        rating = this.rating.toFloat(),
        firstAirDate = this.releaseDate,
        backdropPath = this.backdropPath ?: "",
        originalLanguage = "en",
        originalName = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        adult = false,
        originCountry = emptyList()
    )
}

