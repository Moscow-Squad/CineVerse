package com.moscow.cineverse.screen.explore

import com.android.domain.model.Genre
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

interface ExploreInteractionListener {
    fun onGenreSelected(genre: Genre)
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun onTabSelected(tab: ExploreTabsPages)
    fun onRefresh()
    fun searchMovie()
    fun searchSeries()
    fun searchActor()
    fun getSavedHistoryItems(suggestion: String)
    fun onSearchBarClickedOn()
    fun onCancelButtonClicked()
    fun onSearchValueChange(text: String)
    fun SuggestionList() : List<SuggestItemUiState>
    fun onClickSuggestion(text: String)
    fun clearAllLocalSuggestions()
    fun getMoviesGenres()
    fun getSeriesGenres()
    fun onKeyboardClick()
}