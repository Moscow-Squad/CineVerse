package com.moscow.mapper

import com.moscow.domain.model.Actor
import com.moscow.domain.model.Actor.Gender
import com.moscow.domain.model.Movie
import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.actor.ActorDetailsDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate

fun ActorDto.toDomain() =
    Actor(
        id = id ?: 0,
        name = name.orEmpty(),
        gender = if (gender == 0) Gender.MALE else Gender.FEMALE,
        profileImg = IMAGES_URL + profilePath.orEmpty(),
        placeOfBirth = "",
        birthDate = null,
        socialMediaLinks = Actor.SocialMediaLinks(
            youtube = null,
            facebook = null,
            instagram = null
        ),
        biography = ""
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
        ),
        gender = if (gender == 0) Gender.MALE else Gender.FEMALE,
    )

fun ActorImagesDto.ActorImageDetails.toDomain() =
    IMAGES_URL + filePath.orEmpty()

fun ActorBestOfMoviesDto.ActorBestOfMoviesAsCrew.toDomain() =
    Movie(
        id = id ?: 0,
        title = title.orEmpty(),
        genreIds = genreIds,
        voteAverage = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isNotBlank()) LocalDate.parse(releaseDate) else null,
        backdropUrl = backdropPath.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = IMAGES_URL + posterPath.orEmpty(),
        trailerUrl = "",
        genres = emptyList(),
        duration = Movie.Duration(0, 0)
    )

fun ActorBestOfMoviesDto.ActorBestOfMoviesAsCast.toDomain() =
    Movie(
        id = id ?: 0,
        title = title.orEmpty(),
        genreIds = genreIds,
        voteAverage = voteAverage?.toFloat() ?: 0f,
        releaseDate = if (releaseDate.isNotBlank()) LocalDate.parse(releaseDate) else null,
        backdropUrl = backdropPath.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = IMAGES_URL + posterPath.orEmpty(),
        trailerUrl = "",
        genres = emptyList(),
        duration = Movie.Duration(0, 0)

    )
