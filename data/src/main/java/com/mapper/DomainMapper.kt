package com.mapper

import com.android.domain.model.Actor
import com.android.domain.model.ActorDetails
import com.android.domain.model.Gender
import com.android.domain.model.Genre
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import com.remote.dto.ActorBestOfMoviesAsCastDto
import com.remote.dto.ActorBestOfMoviesAsCrewDto
import com.remote.dto.ActorDto
import com.remote.dto.ActorImagesDto
import com.remote.dto.GenreDto
import com.remote.dto.MovieDto
import com.remote.dto.MultiSearchDto
import com.remote.dto.details.ActorDetailsDto
import com.remote.dto.details.SeriesDto
import com.utils.IMAGES_URL
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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

fun ActorImagesDto.toDomain() =
    IMAGES_URL + filePath.orEmpty()

fun ActorBestOfMoviesAsCrewDto.toDomain() =
    Movie(
        id = id ?: 0,
        name = title.orEmpty(),
        genreIds = genreIds ?: emptyList(),
        rating = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isNullOrEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(releaseDate)
        },
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        video = video ?: false,
        poster = IMAGES_URL + posterPath.orEmpty()
    )

fun ActorBestOfMoviesAsCastDto.toDomain() =
    Movie(
        id = id ?: 0,
        name = title.orEmpty(),
        genreIds = genreIds ?: emptyList(),
        rating = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isNullOrEmpty()) {
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            LocalDate.parse(releaseDate)
        },
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        video = video ?: false,
        poster = IMAGES_URL + posterPath.orEmpty()
    )