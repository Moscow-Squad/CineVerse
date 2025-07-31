package com.moscow.cineverse.mapper

import android.content.Context
import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.common_ui_state.ReviewUiState
import com.moscow.cineverse.common_ui_state.StarCastUiState
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.screen.movie_details.MovieScreenState
import com.moscow.domain.model.CastDetails
import com.moscow.domain.model.CrewDetails
import com.moscow.domain.model.Review
import com.moscow.domain.model.details.MovieDetail
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale

fun MovieDetail.toUi(): MovieScreenState.MovieDetailsUiState =
    MovieScreenState.MovieDetailsUiState(
        id = id,
        title = title,
        posterPath = posterPath,
        rating = (voteAverage * 10).toInt() / 10.0,
        genres = genres,
        releaseDate = releaseDate,
        description = overview,
        duration = duration
    )


fun Review.toUi(): ReviewUiState =
    ReviewUiState(
        id = id,
        username = username,
        name = author,
        userImage = avatarPath,
        rate = rating.toInt(),
        reviewContent = content,
        date = createdAt,

        )

fun CastDetails.toUi() = StarCastUiState(
    id = id,
    originalName = originalName,
    characterName = characterName,
    profileImage = profileImg
)

fun CrewDetails.toUi() = CrewUiState(
    id = id,
    name = name,
    job = job,
)

fun LocalDate.toFormattedReleasedDate(): String {

    return try {
        val isArabic = Locale.getDefault().language == "ar"

        val monthName = if (isArabic) {
            when (this.monthNumber) {
                1 -> "يناير"
                2 -> "فبراير"
                3 -> "مارس"
                4 -> "أبريل"
                5 -> "مايو"
                6 -> "يونيو"
                7 -> "يوليو"
                8 -> "أغسطس"
                9 -> "سبتمبر"
                10 -> "أكتوبر"
                11 -> "نوفمبر"
                12 -> "ديسمبر"
                else -> "غير معروف"
            }
        } else {
            when (this.monthNumber) {
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
        }

        val separator = if (isArabic) "، " else ", "
        String.format(
            Locale.getDefault(),
            "%d%s%s %d",
            this.year,
            separator,
            monthName,
            this.dayOfMonth
        )
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

fun Int.toHourMinuteFormat(context: Context): String {
    val hours = this / 60
    val minutes = this % 60

    return when {
        hours > 0 && minutes > 0 -> context.getString(
            R.string.duration_hours_minutes,
            hours,
            minutes
        )

        hours > 0 -> context.getString(R.string.duration_hours_only, hours)
        minutes > 0 -> context.getString(R.string.duration_minutes_only, minutes)
        else -> ""
    }
}