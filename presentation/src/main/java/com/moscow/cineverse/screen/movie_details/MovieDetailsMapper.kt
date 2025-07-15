package com.moscow.cineverse.screen.movie_details

import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUi
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUi
import com.moscow.cineverse.screen.movie_details.MovieScreenState.ReviewUi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

const val YYYY_MMM_DD = "yyyy, MMM dd"

fun MovieDetail.toUi(): MovieScreenState.MovieDetailsUi =
    MovieScreenState.MovieDetailsUi(
        id = id,
        title = title,
        posterPath = posterPath?:"",
        rating = voteAverage,
        genres = genres,
        releaseDate = releaseDate?.toLocalDate()?.formatWith(YYYY_MMM_DD) ?: "",
        description = overview,
        duration = ""
    )


fun Review.toUi(): ReviewUi =
    ReviewUi(
        id = id.toInt(),
        username = username,
        name = author,
        userImage = avatarPath,
        rate = rating.toInt(),
        reviewContent = content,
        date=createdAt,

    )

fun Actor.toUi(): ActorUi =
    ActorUi(
        id = id,
        title = name,
        profilePath = profileImg
    )

fun Genre.toUi() =
    GenreUi(
        id = id,
        name = name
    )



fun LocalDate.formatWith(pattern: String): String? {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    val year = year.toString()

    return runCatching {
        pattern.replace("dd", day)
            .replace("MM", month)
            .replace("yyyy", year)
    }.getOrElse {
        it.printStackTrace()
        null
    }
}