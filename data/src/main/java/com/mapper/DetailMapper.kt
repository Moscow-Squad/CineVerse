package com.mapper


import com.android.domain.model.MovieDetail
import com.remote.dto.details.MovieDetailDto

fun MovieDetailDto.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genres = genres.map { it.name }
    )
}