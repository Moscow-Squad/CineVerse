package com.mapper

import com.android.domain.exception.CineVerseException
import com.android.domain.model.Actor
import com.android.domain.model.ActorDetails
import com.android.domain.model.Gender
import com.android.domain.model.Genre
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import com.remote.dto.ActorBestOfMoviesAsCastDto
import com.remote.dto.ActorDetailsDto
import com.remote.dto.ActorBestOfMoviesAsCrewDto
import com.remote.dto.ActorDto
import com.remote.dto.ActorImagesDto
import com.remote.dto.GenreDto
import com.remote.dto.MovieDto
import com.remote.dto.MultiSearchDto
import com.remote.dto.SeriesDto
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
        poster = "",
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
        gender = if (gender == 0) Gender.MALE else Gender.FEMALE,
        profileImg = profilePath.orEmpty(),
    )

fun GenreDto.toDomain() =
    Genre(
        id = id,
        name = name
    )

fun ActorDetailsDto.toDomain(youtubeLink: String, facebookLink: String, instagramLink: String) =
    ActorDetails(
        id = id?: throw CineVerseException.MappingToDomainException,
        birthDate = if (birthday == null) throw CineVerseException.MappingToDomainException else LocalDate.parse(birthday),
        placeOfBirth = placeOfBirth ?: throw CineVerseException.MappingToDomainException,
        youtubeLink = youtubeLink,
        facebookLink = facebookLink,
        instagramLink = instagramLink,
        biography = biography ?: throw CineVerseException.MappingToDomainException,
        profileImg = IMAGES_URL + (profilePath ?: throw CineVerseException.MappingToDomainException)
    )

fun ActorImagesDto.toDomain() =
    IMAGES_URL + (filePath ?: throw CineVerseException.MappingToDomainException)

fun ActorBestOfMoviesAsCrewDto.toDomain() = 
    Movie(
        id = id ?: throw CineVerseException.MappingToDomainException,
        name = title ?: throw CineVerseException.MappingToDomainException,
        genreIds = genreIds,
        rating = (popularity ?: throw CineVerseException.MappingToDomainException).toFloat(),
        releaseDate = if(releaseDate == null) throw CineVerseException.MappingToDomainException else LocalDate.parse(releaseDate),
        adult = adult ?: throw CineVerseException.MappingToDomainException,
        backdropPath = backdropPath ?: throw CineVerseException.MappingToDomainException,
        originalLanguage = originalLanguage ?: throw CineVerseException.MappingToDomainException,
        originalTitle = originalTitle ?: throw CineVerseException.MappingToDomainException,
        overview = overview ?: throw CineVerseException.MappingToDomainException,
        posterPath = IMAGES_URL + (posterPath ?: throw CineVerseException.MappingToDomainException),
        video = video ?: throw CineVerseException.MappingToDomainException,
        poster = IMAGES_URL + posterPath
    )

fun ActorBestOfMoviesAsCastDto.toDomain() =
    Movie(
        id = id ?: throw CineVerseException.MappingToDomainException,
        name = title ?: throw CineVerseException.MappingToDomainException,
        genreIds = genreIds,
        rating = (popularity ?: throw CineVerseException.MappingToDomainException).toFloat(),
        releaseDate = if(releaseDate == null) throw CineVerseException.MappingToDomainException else LocalDate.parse(releaseDate),
        adult = adult ?: throw CineVerseException.MappingToDomainException,
        backdropPath = backdropPath ?: throw CineVerseException.MappingToDomainException,
        originalLanguage = originalLanguage ?: throw CineVerseException.MappingToDomainException,
        originalTitle = originalTitle ?: throw CineVerseException.MappingToDomainException,
        overview = overview ?: throw CineVerseException.MappingToDomainException,
        posterPath = IMAGES_URL + (posterPath ?: throw CineVerseException.MappingToDomainException),
        video = video ?: throw CineVerseException.MappingToDomainException,
        poster = IMAGES_URL + posterPath
    )