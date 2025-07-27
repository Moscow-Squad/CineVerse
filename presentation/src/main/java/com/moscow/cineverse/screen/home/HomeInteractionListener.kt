package com.moscow.cineverse.screen.home

import com.moscow.cineverse.common_ui_state.MediaItemUiState

interface HomeInteractionListener {
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onSeeAllClick(type: HomeFeaturedItems)
    fun onCollectionsShowMoreClick()
    fun onCollectionClick(collectionId: Int)
    fun onPromotionClick(promotionId: Int)
    fun onWatchSuggestionClick()
    fun onBrowseSuggestionClick()
    fun onRefresh()
}