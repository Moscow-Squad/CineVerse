package com.moscow.cineverse.screen.explore

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.common_ui_state.MediaItemUiState

@Immutable
@Stable
data class ExploreScreenState(

    val searchKeyWord: String = "",

    val searchResult: Map<String, List<Any>> = mutableMapOf(),

    val remoteSuggestions: List<String> = emptyList(),
    val localSuggestions: List<SuggestItemUiState> = listOf(),
    val isSearchBarClickedOn: Boolean = false,
    val showHistory: Boolean = false,
    val showSuggestions: Boolean = false,

    val genres: List<GenreUiState> = emptyList(),

    val moviesGenres: List<GenreUiState> = emptyList(),
    val seriesGenres: List<GenreUiState> = emptyList(),

    val movies: List<MediaItemUiState> = emptyList(),
    val series: List<MediaItemUiState> = emptyList(),

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

    val displayedSuggestions: List<SuggestItemUiState>
        get() {
            val filteredLocalSuggestions = localSuggestions
                .filter { it.title.contains(searchKeyWord, ignoreCase = true) }

            val mappedRemoteSuggestions = remoteSuggestions
                .map { SuggestItemUiState(it, isHistory = false) }

            return filteredLocalSuggestions + mappedRemoteSuggestions
        }

    fun fromScreenState(selectedTab: ExploreTabsPages): List<MediaItemUiState> {
        return when (selectedTab) {
            ExploreTabsPages.MOVIES -> movies
            ExploreTabsPages.SERIES -> series
            else -> emptyList()
        }
    }

    data class ActorUiState(
        val title: String,
        val profilePath: String,
        val id: Int
    )

    data class GenreUiState(
        val id: Int,
        val name: String
    )
}

