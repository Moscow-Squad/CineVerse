package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import kotlinx.datetime.LocalDate

data class ExploreScreenState(
    val genres: List<Genre> = emptyList(),
    val movies: List<Movie> = emptyList(),
    val series: List<Series> = emptyList(),
    val selectedGenre: Genre? = null,
    val selectedTab: ExploreTabsPages = ExploreTabsPages.MOVIES,
    val viewMode: ViewMode = ViewMode.GRID,
    val isLoading: Boolean = false,
    val error: String? = null
)
data class UiMovie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val duration: String,
    val releaseDate: String,
    val genres: List<String> = emptyList(),
    val description: String = ""
)

fun Movie.toUiMovie(genreNameResolver: (Int) -> String = { "" }): UiMovie {

    return UiMovie(
        id = id,
        title = name,
        posterUrl = poster,
        rating = rating,
        duration = duration,
        releaseDate = releaseDate.formatWith(YYYY_MMM_DD)?:"",
        description = description,
        genres = genresId.map(genreNameResolver)
    )
}
const val YYYY_MMM_DD = "yyyy, MMM dd"
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