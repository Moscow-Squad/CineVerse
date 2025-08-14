package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.utlis.ViewMode

interface ExploreInteractionListener {
    fun onMovieGenreSelected(genreId: Int)
    fun onSeriesGenreSelected(genreId: Int)
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onActorClick(actorId: Int)
    fun onTabSelected(tab: ExploreTabsPages)
    fun onRefresh()
    fun searchMovie()
    fun searchSeries()
    fun searchActor()
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