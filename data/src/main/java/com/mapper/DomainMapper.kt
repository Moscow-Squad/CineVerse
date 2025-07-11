package com.mapper

import com.android.domain.model.Actor
import com.android.domain.model.Gender
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import com.remote.dto.ActorDto
import com.remote.dto.MovieDto
import com.remote.dto.MultiSearchDto
import com.remote.dto.SeriesDto
import java.time.LocalDate

fun MultiSearchDto.toDomain() =
    MultiSearch(
        id = id ?: 0,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        mediaType = MediaType.toMediaType(mediaType.orEmpty()),
        adult = adult == true,
        backdropPath = backdropPath.orEmpty(),
        firstAirDate = firstAirDate.orEmpty(),
        genreIds = genreIds ?: emptyList(),
        name = name.orEmpty(),
        originalName = originalName.orEmpty(),
        popularity = popularity ?: 0.0,
        rate = voteAverage ?: 0f,
    )

fun MovieDto.toDomain() =
    Movie(
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        adult = adult == true,
        releaseDate = if (releaseDate.isNullOrEmpty()) {
            LocalDate.now()
        } else {
            LocalDate.parse(releaseDate)
        },
        genreIds = genreIds ?: emptyList(),
        originalLanguage = originalLanguage.orEmpty(),
        rating = voteAverage ?: 0f,
        id = id ?: 0,
        name = title.orEmpty(),
        poster = posterPath.orEmpty(),
        video = video == true,
    )

fun SeriesDto.toDomain() =
    Series(
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        adult = adult == true,
        firstAirDate = if (firstAirDate.isNullOrEmpty()) {
            LocalDate.now()
        } else {
            LocalDate.parse(firstAirDate)
        },
        genreIds = genreIds ?: emptyList(),
        originalLanguage = originalLanguage.orEmpty(),
        id = id ?: 0,
        name = name.orEmpty(),
        poster = posterPath.orEmpty(),
        originCountry = originCountry ?: emptyList(),
        originalName = originalName.orEmpty(),
        rating = voteAverage ?: 0f,
    )

fun ActorDto.toDomain() =
    Actor(
        id = id ?: 0,
        name = name.orEmpty(),
        gender = if (gender == 0) Gender.MALE else Gender.FEMALE,
        profileImg = profilePath.orEmpty(),
    )