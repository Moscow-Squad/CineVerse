package com.moscow.cineverse.screen.home

import com.moscow.cineverse.designSystem.component.ViewMode


interface SeeMoreHomeInteractionListener {
    fun onRefresh()
    fun onMediaItemClicked(id: Int)
    fun onActorClick(id: Int)
    fun onNavigateBack()
    fun onViewModeChanged(viewMode: ViewMode)
}