package com.moscow.mapper

import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.CreditsInfo.CastInfo
import com.moscow.domain.model.CreditsInfo.CrewInfo
import com.moscow.domain.model.Movie
import com.moscow.local.entity.HistoryItemEntity
import com.moscow.local.entity.MediaItemEntity
import com.moscow.remote.dto.media_item.common.CastDetailsDto
import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.common.CrewDetailsDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.media_item.movie.MovieDetailDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate

const val ITEM_MOVIE = "movie"

fun MovieDto.toDomain() =
    Movie(
        overview = overview.orEmpty(),
        posterUrl = IMAGES_URL + posterPath.orEmpty(),
        backdropUrl = IMAGES_URL + backdropPath.orEmpty(),
        releaseDate = if (!releaseDate.isNullOrBlank()) LocalDate.parse(releaseDate) else null,
        genreIds = genreIds ?: emptyList(),
        id = id ?: 0,
        title = title.orEmpty(),
        genres = emptyList(),
        duration = Movie.Duration(0, 0),
        rating = voteAverage ?: 0.0f,
        trailerUrl = "",
    )

fun Movie.toHomeItemEntity(categoryType: String): MediaItemEntity {
    return MediaItemEntity(
        itemId = this.id,
        categoryType = categoryType,
        name = this.title,
        posterPath = this.posterUrl,
        backdropPath = this.backdropUrl,
        itemType = ITEM_MOVIE,
        rating = this.rating,
        genreIds = this.genreIds,
        releaseDate = this.releaseDate
    )
}


fun Movie.toHistoryItemEntity(): HistoryItemEntity {
    return HistoryItemEntity(
        id = this.id,
        name = this.title,
        posterPath = this.posterUrl,
        itemType = ITEM_MOVIE,
        rating = this.rating,
        releaseDate = this.releaseDate
    )
}

fun MediaItemEntity.toMovie(
    overview: String = "",
): Movie {
    return Movie(
        id = this.itemId,
        title = this.name,
        genreIds = this.genreIds,
        rating = this.rating,
        releaseDate = this.releaseDate,
        backdropUrl = this.backdropPath,
        overview = overview,
        posterUrl = this.posterPath,
        duration = Movie.Duration(0, 0),
        trailerUrl = "",
        genres = emptyList()
    )
}


fun HistoryItemEntity.toMovie(
    overview: String = "",
): Movie {
    return Movie(
        id = this.id,
        title = this.name,
        rating = this.rating,
        releaseDate = this.releaseDate,
        overview = overview,
        posterUrl = this.posterPath,
        backdropUrl = this.posterPath,
        genreIds = emptyList(),
        duration = Movie.Duration(0, 0),
        trailerUrl = "",
        genres = emptyList()
    )
}

fun MovieDetailDto.toDomain(trailer: String): Movie {
    return Movie(
        id = id ?: 0,
        title = title ?: "",
        overview = overview ?: "",
        trailerUrl = "https://youtu.be/$trailer",
        posterUrl = IMAGES_URL + posterPath,
        releaseDate = if (!releaseDate.isNullOrBlank()) LocalDate.parse(releaseDate) else null,
        rating = voteAverage ?: 0.0f,
        genres = genres?.map { it.name } ?: emptyList(),
        genreIds = emptyList(),
        duration = Movie.Duration(
            hours = runtime?.div(60) ?: 0,
            minutes = runtime?.rem(60) ?: 0
        ),
        backdropUrl = IMAGES_URL + backdropPath.orEmpty()
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
    )
