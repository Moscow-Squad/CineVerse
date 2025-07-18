package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.model.MediaItemUi

interface ExploreInteractionListener {
    fun onMovieGenreSelected(genreId: Int)
    fun onSeriesGenreSelected(genreId: Int)
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMediaItemClicked(mediaItemUi: MediaItemUi)
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
    fun onSearchWordDetected(searchKeyWord: List<String>)
    fun onClickSuggestion(suggestion: SuggestItemUiState)
    fun onSearchQuery()
    fun clearAllLocalSuggestions()
    fun getMoviesGenres()
    fun getSeriesGenres()
    fun getMoviesByGenreId(genreId: Int)
    fun getSeriesByGenreId(genreId: Int)
    fun onKeyboardClick()
    fun <T> checkEmptySearchResult(list: List<T>)}