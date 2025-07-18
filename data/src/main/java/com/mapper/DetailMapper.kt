package com.mapper


import com.android.domain.model.CastDetails
import com.android.domain.model.CreditsDetails
import com.android.domain.model.CrewDetails
import com.android.domain.model.details.MovieDetail
import com.remote.dto.CastDetailsDto
import com.remote.dto.CreditsDetailsDto
import com.remote.dto.CrewDetailsDto
import com.remote.dto.details.MovieDetailDto
import com.utils.IMAGES_URL
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MovieDetailDto.toDomain(): MovieDetail {
    return MovieDetail(
        id = id?:0,
        title = title?:"",
        overview = overview?:"",
        posterPath = IMAGES_URL + posterPath,
        releaseDate = if (releaseDate == null) Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date else LocalDate.parse(releaseDate),
        voteAverage = voteAverage?:0.0,
        genres = genres?.map { it.name }?:emptyList(),
        duration = runtime?:0
    )
}
fun CreditsDetailsDto.toDomain(): CreditsDetails =
    CreditsDetails(
        actors =
            cast?.mapNotNull { it?.toDomain() }?:emptyList(),
        behindTheScene =
            crew?.mapNotNull { it?.toDomain() }?:emptyList()

    )
fun CastDetailsDto.toDomain(): CastDetails =
    CastDetails(
        id = id?:0,
        originalName = name?:"",
        characterName = character?:"",
        profileImg =  profilePath?.let { IMAGES_URL + it } ?: ""
    )

fun CrewDetailsDto.toDomain() : CrewDetails =
    CrewDetails(
        id = id?:0,
        name = originalName?:"",
        job = job?:"",
        profileImage =  profilePath?.let { IMAGES_URL + it } ?: ""
    )