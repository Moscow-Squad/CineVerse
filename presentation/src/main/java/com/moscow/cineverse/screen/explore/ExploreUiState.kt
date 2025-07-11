package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages

data class ExploreUiState(
    val contentList: List<Any> = emptyList(), // Can be Movie or Series
    val isContentEmpty: Boolean = false,
    val shouldShowGenres: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = ""
) {
    companion object {
        fun fromScreenState(screenState: ExploreScreenState): ExploreUiState {
            val contentList = when (screenState.selectedTab) {
                ExploreTabsPages.MOVIES -> screenState.movies
                ExploreTabsPages.SERIES -> screenState.series
                else -> emptyList()
            }

            return ExploreUiState(
                contentList = contentList,
                isContentEmpty = contentList.isEmpty() && !screenState.isLoading,
                shouldShowGenres = screenState.genres.isNotEmpty(),
                shouldShowLoading = screenState.isLoading,
                shouldShowError = screenState.error != null,
                errorMessage = screenState.error ?: ""
            )
        }
    }
}