package com.moscow.mapper

import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.CreditsInfo.CastInfo
import com.moscow.domain.model.CreditsInfo.CrewInfo
import com.moscow.domain.model.details.MovieDetail
import com.moscow.remote.dto.CastDetailsDto
import com.moscow.remote.dto.CreditsDetailsDto
import com.moscow.remote.dto.CrewDetailsDto
import com.moscow.remote.dto.details.MovieDetailDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate
import kotlin.collections.map

fun MovieDetailDto.toDomain(trailer: String): MovieDetail {
    return MovieDetail(
        id = id ?: 0,
        title = title ?: "",
        overview = overview ?: "",
        trailerPath = "https://youtu.be/$trailer",
        posterPath = IMAGES_URL + posterPath,
        releaseDate = if(!releaseDate.isNullOrBlank()) LocalDate.parse(releaseDate) else null,
        voteAverage = voteAverage ?: 0.0,
        genres = genres?.map { it.name } ?: emptyList(),
        duration = runtime ?: 0
    )
}

fun CreditsDetailsDto.toDomain(): CreditsInfo =
    CreditsInfo(
        cast =
            cast?.mapNotNull { it?.toDomain() } ?: emptyList(),
        crew =
            crew?.mapNotNull { it?.toDomain() } ?: emptyList()
    )

fun CastDetailsDto.toDomain(): CastInfo =
    CastInfo(
        id = id ?: 0,
        originalName = name ?: "",
        characterName = character ?: "",
        profileImg = profilePath?.let { IMAGES_URL + it } ?: ""
    )

fun CrewDetailsDto.toDomain(): CrewInfo =
    CrewInfo(
        id = id ?: 0,
        name = originalName ?: "",
        job = job ?: "",
        profileImage = profilePath?.let { IMAGES_URL + it } ?: ""
    )