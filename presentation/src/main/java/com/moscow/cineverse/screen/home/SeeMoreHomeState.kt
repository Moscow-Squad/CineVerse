package com.moscow.cineverse.screen.home

import com.moscow.cineverse.designSystem.component.ViewMode

data class SeeMoreHomeState(
    val title: String,
    val isLoading: Boolean = false,
    val isContentEmpty: Boolean = false,
    val shouldShowError: Boolean = false,
    val viewMode: ViewMode = ViewMode.GRID
)
