package com.moscow.cineverse.screen.see_more

import com.moscow.cineverse.utlis.ViewMode

data class SeeMoreUiState(
    val title: String = "",
    val isLoading: Boolean = false,
    val isContentEmpty: Boolean = false,
    val shouldShowError: Boolean = false,
    val viewMode: ViewMode = ViewMode.GRID,
    val enableBlur: Boolean = true
)
