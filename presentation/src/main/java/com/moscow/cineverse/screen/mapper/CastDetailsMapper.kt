package com.moscow.cineverse.screen.mapper

import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import kotlinx.datetime.LocalDate

fun LocalDate.toFormattedBirthDate(): String {
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
        "Born on $monthName ${this.dayOfMonth}, ${this.year}"
    } catch (e: Exception) {
        "Born on $this"
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
