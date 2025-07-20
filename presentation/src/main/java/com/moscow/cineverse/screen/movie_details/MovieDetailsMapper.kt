package com.moscow.cineverse.screen.movie_details

import com.android.domain.model.CastDetails
import com.android.domain.model.CrewDetails
import com.android.domain.model.Genre
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.Review
import com.android.domain.model.details.MovieDetail
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUi
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.movie_details.MovieScreenState.ReviewUi
import com.moscow.cineverse.screen.movie_details.MovieScreenState.StarCastUi
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale


fun MovieDetail.toUi(): MovieScreenState.MovieDetailsUi =
    MovieScreenState.MovieDetailsUi(
        id = id,
        title = title,
        posterPath = posterPath,
        rating = (voteAverage * 10).toInt() / 10.0,
        genres = genres,
        releaseDate = releaseDate,
        description = overview,
        duration = duration
    )


fun Review.toUi(): ReviewUi =
    ReviewUi(
        id = id,
        username = username,
        name = author,
        userImage = avatarPath,
        rate = rating.toInt(),
        reviewContent = content,
        date=createdAt,

    )

fun CastDetails.toUi(): StarCastUi =
    StarCastUi(
        id = id,
        originalName = originalName,
        characterName = characterName,
        profileImage = profileImg
    )

fun CrewDetails.toUi(): MovieScreenState.CrewUi =
    MovieScreenState.CrewUi(
        id = id,
        name = name,
        job = job,

    )

fun Genre.toUi() =
    GenreUi(
        id = id,
        name = name
    )


fun LocalDate.toFormattedReleasedDate(): String {
    return try {
        val monthName = when (this.monthNumber) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> "Unknown"
        }
        " ${this.year}, $monthName ${this.dayOfMonth}"
    } catch (e: Exception) {
        "$this"
    }
}
fun formatReviewDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

fun Movie.toMediaItemUi(): MediaItemUiState {
    return MediaItemUiState(
        id = this.id,
        title = this.name,
        posterPath = this.posterPath,
        rating = this.rating,
        genres = emptyList(),
        releaseDate = this.releaseDate.toString(),
        duration = "",
        mediaType = MediaType.Movie
    )
}

fun Int.toHourMinuteFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return when {
        hours > 0 && minutes > 0 -> "$hours h $minutes m"
        hours > 0 -> "$hours h"
        minutes > 0 -> "$minutes m"
        else -> ""
    }
}