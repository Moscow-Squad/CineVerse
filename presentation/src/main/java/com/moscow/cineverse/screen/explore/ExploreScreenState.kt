package com.moscow.cineverse.screen.explore

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.component.movie_poster_card.MediaItemUi

@Immutable
@Stable
data class ExploreScreenState(

    val searchKeyWord: String = "",

    val searchResult: Map<String, List<Any>> = mutableMapOf(),

    val remoteSuggestions: List<String> = emptyList(),
    val localSuggestions: List<SuggestItemUiState> = listOf(),
    val displayedSuggestions: List<SuggestItemUiState> = emptyList(),
    val isSearchBarClickedOn: Boolean = false,
    val showHistory: Boolean = false,
    val showSuggestions: Boolean = false,

    val genres: List<GenreUi> = emptyList(),

    val moviesGenres: List<GenreUi> = emptyList(),
    val seriesGenres: List<GenreUi> = emptyList(),

    val movies: List<MediaItemUi> = emptyList(),
    val series: List<MediaItemUi> = emptyList(),

    val selectedMovieGenre: Int = 0,
    val selectedSeriesGenre: Int = 0,

    val selectedTab: ExploreTabsPages = ExploreTabsPages.MOVIES,
    val viewMode: ViewMode = ViewMode.GRID,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isContentEmpty: Boolean = false,
    val shouldShowGenres: Boolean = true,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",
    val contentList: List<Any> = emptyList()

) {
    fun fromScreenState(selectedTab: ExploreTabsPages): List<MediaItemUi> {
        return when (selectedTab) {
            ExploreTabsPages.MOVIES -> movies
            ExploreTabsPages.SERIES -> series
            else -> emptyList()
        }
    }

    data class ActorUi(
        val title: String,
        val profilePath: String,
        val id: Int
    )

    data class GenreUi(
        val id: Int,
        val name: String
    )
}

