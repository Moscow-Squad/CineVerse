package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.designSystem.component.MovieUI
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

data class ExploreScreenState(

    val searchKeyWord: String = "",

    val searchResult: Map<String, List<MovieUI>> = mutableMapOf(),

    val actorsSearchResult: List<ActorUi> = emptyList(),
    val remoteSuggestions:List<String> = emptyList(),

    val isSearchBarClickedOn : Boolean = false,
    val showHistory : Boolean = false,
    val showSuggestions : Boolean = false,
    val localSuggestions: List<SuggestItemUiState> = listOf(),

    val genres: List<GenreUi> = emptyList(),

    val moviesGenres: List<GenreUi> = emptyList(),
    val seriesGenres: List<GenreUi> = emptyList(),

    val moviesUI: List<MovieUI> = emptyList(),
    val series: List<MovieUI> = emptyList(),

    val selectedGenre: Int = 0,
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
    fun fromScreenState(selectedTab: ExploreTabsPages): List<MovieUI> {
        return when (selectedTab) {
            ExploreTabsPages.MOVIES -> moviesUI
            ExploreTabsPages.SERIES -> series
            else -> emptyList()
        }
    }



    data class ActorUi(
        val title: String,
        val icon: String,
        val id: Int
    )
    data class GenreUi(
        val id: Int,
        val name: String
    )
}

