package com.moscow.cineverse.screen.explore

import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUi
import com.moscow.cineverse.screen.explore.ExploreScreenState.MediaItemUi
import kotlinx.datetime.LocalDate

const val YYYY_MMM_DD = "yyyy, MMM dd"

fun Movie.toUi(): MediaItemUi =
    MediaItemUi(
        id = id,
        name = name,
        posterPath = posterPath,
        rating = rating,
        genres = emptyList(),
        releaseDate = releaseDate.formatWith(YYYY_MMM_DD) ?: ""
    )

fun Series.toUi(): MediaItemUi =
    MediaItemUi(
        id = id,
        name = name,
        posterPath = posterPath,
        rating = rating,
        genres = emptyList(),
        releaseDate = firstAirDate.formatWith(YYYY_MMM_DD) ?: ""
    )

fun Actor.toUi(): ActorUi =
    ActorUi(
        title = name,
        icon = profileImg,
        id = id
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