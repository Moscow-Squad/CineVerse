package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

data class ExploreScreenState(
    val genres: List<Genre> = emptyList(),
    val movies: List<MediaItemUi> = emptyList(),
    val series: List<MediaItemUi> = emptyList(),
    val selectedGenre: Genre? = null,
    val selectedTab: ExploreTabsPages = ExploreTabsPages.MOVIES,
    val viewMode: ViewMode = ViewMode.GRID,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isContentEmpty: Boolean = false,
    val shouldShowGenres: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",
    val contentList: List<MediaItemUi> = emptyList()

) {
    fun fromScreenState(selectedTab: ExploreTabsPages): List<MediaItemUi> {
        return when (selectedTab) {
            ExploreTabsPages.MOVIES -> movies
            ExploreTabsPages.SERIES -> series
            else -> emptyList()
        }
    }

    data class MediaItemUi(
        val id: Int,
        val name: String,
        val posterPath: String,
        val rating: Float,
        val genres: List<String>,
        val releaseDate: String,
    )

    data class ActorUi(
        val title: String,
        val icon: String,
        val id: Int
    )

}

