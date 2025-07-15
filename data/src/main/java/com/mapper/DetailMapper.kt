package com.mapper


import com.android.domain.model.MovieDetail
import com.android.domain.model.SeriesDetail
import com.remote.dto.MovieDetailDto
import com.remote.dto.SeriesDetailDto

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