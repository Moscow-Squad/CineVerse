package com.moscow.mapper

import com.android.domain.model.Actor
import com.android.domain.model.ActorDetails
import com.android.domain.model.Gender
import com.android.domain.model.Genre
import com.android.domain.model.MediaItem
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.moscow.remote.dto.GenreDto
import com.moscow.remote.dto.MediaItemDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.details.ActorDetailsDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MediaItemDto.toDomain() =
    MediaItem(
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
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        backdropPath = IMAGES_URL + backdropPath.orEmpty(),
        adult = adult == true,
        releaseDate = if (releaseDate.isNullOrEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(releaseDate)
        },
        genreIds = genreIds ?: emptyList(),
        originalLanguage = originalLanguage.orEmpty(),
        rating = voteAverage ?: 0f,
        id = id ?: 0,
        name = title.orEmpty(),
        video = video == true,
        poster = posterPath.orEmpty(),
    )

fun SeriesDto.toDomain() =
    Series(
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        backdropPath = IMAGES_URL + backdropPath.orEmpty(),
        adult = adult == true,
        firstAirDate = if (firstAirDate.isNullOrEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(firstAirDate)
        },
        genreIds = genreIds ?: emptyList(),
        originalLanguage = originalLanguage.orEmpty(),
        id = id ?: 0,
        name = name.orEmpty(),
        originCountry = originCountry ?: emptyList(),
        originalName = originalName.orEmpty(),
        rating = voteAverage ?: 0f,
    )

fun ActorDto.toDomain() =
    Actor(
        id = id ?: 0,
        name = name.orEmpty(),
        gender = if (gender == Gender.MALE.value) Gender.MALE else Gender.FEMALE,
        profileImg = IMAGES_URL + profilePath.orEmpty(),
    )

fun GenreDto.toDomain() =
    Genre(
        id = id,
        name = name
    )

fun ActorDetailsDto.toDomain(youtubeLink: String, facebookLink: String, instagramLink: String) =
    ActorDetails(
        id = id ?: 0,
        name = name.orEmpty(),
        birthDate = if (birthday.isNullOrEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(birthday)
        },
        placeOfBirth = placeOfBirth.orEmpty(),
        youtubeLink = "https://www.youtube.com/user/$youtubeLink",
        facebookLink = "https://www.facebook.com/$facebookLink",
        instagramLink = "https://www.instagram.com/$instagramLink",
        biography = biography.orEmpty(),
        profileImg = IMAGES_URL + profilePath.orEmpty()
    )

fun ActorImagesDto.ActorImageDetails.toDomain() =
    IMAGES_URL + filePath.orEmpty()

fun ActorBestOfMoviesDto.ActorBestOfMoviesAsCrew.toDomain() =
    Movie(
        id = id ?: 0,
        name = title.orEmpty(),
        genreIds = genreIds,
        rating = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(releaseDate)
        },
        adult = adult == true,
        backdropPath = backdropPath.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        video = video == true,
        poster = IMAGES_URL + posterPath.orEmpty()
    )

fun ActorBestOfMoviesDto.ActorBestOfMoviesAsCast.toDomain() =
    Movie(
        id = id ?: 0,
        name = title.orEmpty(),
        genreIds = genreIds,
        rating = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(releaseDate)
        },
        adult = adult == true,
        backdropPath = backdropPath.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        video = video == true,
        poster = IMAGES_URL + posterPath.orEmpty()
    )
