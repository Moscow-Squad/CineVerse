package com.moscow.cineverse.screen.see_more

import com.moscow.cineverse.utlis.ViewMode

interface SeeMoreInteractionListener {
    fun onRefresh()
    fun onMediaItemClicked(id: Int)
    fun onActorClick(id: Int)
    fun onNavigateBack()
    fun onViewModeChanged(viewMode: ViewMode)
}