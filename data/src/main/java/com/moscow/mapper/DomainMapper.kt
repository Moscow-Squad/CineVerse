package com.moscow.mapper

import com.moscow.domain.model.Actor
import com.moscow.domain.model.actor.Gender
import com.moscow.domain.model.Genre
import com.moscow.domain.model.MediaItem
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.remote.dto.GenreDto
import com.moscow.remote.dto.MediaItemDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.details.ActorDetailsDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate


fun MediaItemDto.toDomain() =
    MediaItem(
        id = id,
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
        releaseDate = if (!releaseDate.isNullOrBlank()) LocalDate.parse(releaseDate) else null,
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
        firstAirDate = if (!firstAirDate.isNullOrBlank()) LocalDate.parse(firstAirDate) else null,
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
    Actor(
        id = id ?: 0,
        name = name.orEmpty(),
        birthDate = if (!birthday.isNullOrEmpty()) LocalDate.parse(birthday) else null,
        placeOfBirth = placeOfBirth.orEmpty(),
        biography = biography.orEmpty(),
        profileImg = IMAGES_URL + profilePath.orEmpty(),
        socialMediaLinks = Actor.SocialMediaLinks(
            youtube = "https://www.youtube.com/@$youtubeLink",
            facebook = "https://www.facebook.com/$facebookLink",
            instagram = "https://www.instagram.com/$instagramLink",
        )
    )

fun ActorImagesDto.ActorImageDetails.toDomain() =
    IMAGES_URL + filePath.orEmpty()

fun ActorBestOfMoviesDto.ActorBestOfMoviesAsCrew.toDomain() =
    Movie(
        id = id ?: 0,
        name = title.orEmpty(),
        genreIds = genreIds,
        rating = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isNotBlank()) LocalDate.parse(releaseDate) else null,
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
        releaseDate = if (releaseDate.isNotBlank()) LocalDate.parse(releaseDate) else null,
        adult = adult == true,
        backdropPath = backdropPath.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = IMAGES_URL + posterPath.orEmpty(),
        video = video == true,
        poster = IMAGES_URL + posterPath.orEmpty()
    )
