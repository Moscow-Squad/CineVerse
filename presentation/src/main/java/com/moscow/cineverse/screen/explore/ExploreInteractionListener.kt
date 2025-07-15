package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

interface ExploreInteractionListener {
    fun onGenreSelected(genreId: Int)
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun onActorClick(actorId: Int)
    fun onTabSelected(tab: ExploreTabsPages)
    fun onRefresh()
    fun searchMovie(isHistory: Boolean = false)
    fun searchSeries(isHistory: Boolean = false)
    fun searchActor(isHistory: Boolean = false)
    fun getSavedHistoryItems(suggestion: String)
    fun onSearchBarClickedOn()
    fun onCancelButtonClicked()
    fun onSearchValueChange(text: String)
    fun onSearchWordDetected(searchKeyWords: List<String>)
    fun SuggestionList() : List<SuggestItemUiState>
    fun onClickSuggestion(suggestion: SuggestItemUiState)
    fun clearAllLocalSuggestions()
    fun getMoviesGenres()
    fun getSeriesGenres()
    fun getMoviesByGenreId(genreId: Int)
    fun getSeriesByGenreId(genreId: Int)
    fun onKeyboardClick()
}