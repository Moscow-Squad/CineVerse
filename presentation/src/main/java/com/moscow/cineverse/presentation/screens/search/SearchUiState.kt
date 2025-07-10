package com.moscow.cineverse.presentation.screens.search

data class SearchUiState (
    val isLoading: Boolean = false,
    val isSearchBarClickedOn : Boolean = false,
    val showHistory : Boolean = false,
    val history: List<String> = listOf()
)