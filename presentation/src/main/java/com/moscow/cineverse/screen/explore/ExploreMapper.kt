package com.moscow.cineverse.screen.explore

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUi
import com.moscow.cineverse.screen.explore.ExploreScreenState.MediaItemUi
import kotlinx.datetime.LocalDate

import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun Movie.toUi(): MediaItemUi =
    MediaItemUi(
        id = id,
        name = name,
        posterPath = posterPath,
        rating = rating,
        genres = emptyList(),
        releaseDate = releaseDate.formatWith(YYYY_MMM_DD) ?: ""
    )

@RequiresApi(Build.VERSION_CODES.O)
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

const val YYYY_MMM_DD = "yyyy, MMM dd"

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.formatWith(
    format: String,
    locale: Locale = Locale.ENGLISH,
): String? {
    return runCatching {
        this.toJavaLocalDate().format(DateTimeFormatter.ofPattern(format, locale))
    }.getOrElse {
        it.printStackTrace()
        null
    }
}