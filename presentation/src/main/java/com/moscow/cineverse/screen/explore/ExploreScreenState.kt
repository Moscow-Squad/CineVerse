package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

data class ExploreScreenState(
    val searchKeyWord: String = "",

    val searchResult: Map<String, List<MediaItemUi>> = mutableMapOf(),
    val actorsSearchResult: List<ActorUi> = emptyList(),

    val isLoading: Boolean = false,
    val selectedTab: ExploreTabsPages = ExploreTabsPages.MOVIES,
    val remoteSuggestions:List<String> = emptyList(),

    val isSearchBarClickedOn : Boolean = false,
    val showHistory : Boolean = false,
    val showSuggestions : Boolean = false,
    val localSuggestions: List<SuggestItemUiState> = listOf(),

    val moviesGenres: List<GenreUi> = emptyList(),
    val seriesGenres: List<GenreUi> = emptyList(),

    val contentList: List<Any> = emptyList(),
    val isContentEmpty: Boolean = false,
    val shouldShowGenres: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",

    val genres: List<Genre> = emptyList(),
    val movies: List<MediaItemUi> = emptyList(),
    val series: List<MediaItemUi> = emptyList(),
    val selectedGenre: Genre? = null,
    val viewMode: ViewMode = ViewMode.GRID,
    val error: String? = null,

){
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

    data class GenreUi(
        val id: Int,
        val name: String
    )

    data class CombinedData(
        val movies: List<MediaItemUi>,
        val series: List<MediaItemUi>,
        val movieGenres: List<Genre>,
        val seriesGenres: List<Genre>,
        val movieGenreMap: Map<Int, Genre>,
        val seriesGenreMap: Map<Int, Genre>
    )

    fun fromScreenState(selectedTab: ExploreTabsPages): List<MediaItemUi> {
        return when (selectedTab) {
            ExploreTabsPages.MOVIES -> movies
            ExploreTabsPages.SERIES -> series
            else -> emptyList()
        }
    }
}
