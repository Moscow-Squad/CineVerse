package com.moscow.cineverse.screen.myratings

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreTabsPages

interface MyRatingsInteractionListener {
    fun onTabSelected(tab: ExploreTabsPages)
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onNavigateBack()
    fun onRefresh()
}