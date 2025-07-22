package com.moscow.cineverse.screen.explore

import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import kotlinx.datetime.LocalDate

const val YYYY_MMM_DD = "yyyy, MMM dd"

fun Movie.toUi(genresList: List<GenreUiState>): MediaItemUiState =
    MediaItemUiState(
        id = id.toInt(),
        title = name,
        posterPath = posterPath,
        rating = rating,
        genres = genreIds.map { it -> genresList.first { genre -> genre.id == it }.name },
        releaseDate = releaseDate.formatWith(YYYY_MMM_DD) ?: "",
        duration = "",
        mediaType = MediaType.Movie
    )

fun Series.toUi(genresList: List<GenreUiState>): MediaItemUiState =
    MediaItemUiState(
        id = id,
        title = name,
        posterPath = posterPath,
        rating = rating,
        genres = genreIds.map { it -> genresList.first { genre -> genre.id == it }.name },
        releaseDate = firstAirDate.formatWith(YYYY_MMM_DD) ?: "",
        duration = "",
        mediaType = MediaType.Tv
    )

fun Actor.toUi(): ActorUiState =
    ActorUiState(
        title = name,
        profilePath = profileImg,
        id = id
    )

fun Genre.toUi() =
    GenreUiState(
        id = id,
        name = name
    )

fun ExploreTabsPages.toTitle(): String {
    return when (this) {
        ExploreTabsPages.MOVIES -> "Movies"
        ExploreTabsPages.SERIES -> "Series"
        ExploreTabsPages.ACTORS -> "Actors"
    }
}

fun LocalDate.formatWith(pattern: String): String? {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = getMonthName(monthNumber)
    val year = year.toString()

    return runCatching {
        pattern.replace("dd", day)
            .replace("MMM", month)
            .replace("yyyy", year)
    }.getOrElse {
        it.printStackTrace()
        null
    }
}

private fun getMonthName(monthNumber: Int): String {
    return when (monthNumber) {
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