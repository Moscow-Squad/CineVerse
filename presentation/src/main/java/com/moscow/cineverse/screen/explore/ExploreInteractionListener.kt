package com.moscow.cineverse.screen.explore

interface ExploreInteractionListener {
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
}