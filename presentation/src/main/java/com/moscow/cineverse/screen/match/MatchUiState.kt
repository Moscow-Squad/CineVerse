package com.moscow.cineverse.screen.match

data class MatchUiState(
    val isLoading: Boolean = false,
    val currentPage: MatchPages = MatchPages.StartPage,
    val questions: List<String> = emptyList(),
    val answers: List<String> = emptyList(),
)
