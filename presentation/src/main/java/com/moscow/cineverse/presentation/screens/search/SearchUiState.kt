package com.moscow.cineverse.presentation.screens.search

data class SearchUiState (
    val searchText: String = "",
    val isLoading: Boolean = false,
    val isSearchBarClickedOn : Boolean = false,
    val showHistory : Boolean = false,
    val showSuggestions : Boolean = false,
    val history: List<SuggestItemUiState> = listOf()
)