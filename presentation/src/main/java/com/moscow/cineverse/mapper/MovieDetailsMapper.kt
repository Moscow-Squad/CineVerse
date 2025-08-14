package com.moscow.cineverse.mapper

import android.content.Context
import com.moscow.cineverse.common_ui_state.CrewUiState
import com.moscow.cineverse.common_ui_state.ReviewUiState
import com.moscow.cineverse.common_ui_state.CastUiState
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.screen.movie_details.MovieScreenState
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale

fun Movie.toUi(): MovieScreenState.MovieDetailsUiState =
    MovieScreenState.MovieDetailsUiState(
        id = id,
        title = title,
        posterUrl = posterUrl,
        trailerUrl = trailerUrl,
        rating = (rating * 10).toInt() / 10.0,
        genres = genres,
        releaseDate = releaseDate,
        description = overview,
        duration = "${this.duration.hours} h ${this.duration.minutes} m",

    )

fun Review.toUi(): ReviewUiState =
    ReviewUiState(
        id = id,
        username = username,
        name = author,
        userImageUrl = avatarPath,
        rate = rating,
        reviewContent = content,
        date = createdAt
        )

fun CreditsInfo.CastInfo.toUi() = CastUiState(
    id = id,
    originalName = originalName,
    characterName = characterName,
    profileImage = profileImg
)

fun CreditsInfo.CrewInfo.toUi() = CrewUiState(
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
    } catch (_: Exception) {
        "$this"
    }
}

fun formatReviewDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (_: Exception) {
        dateString
    }
}
