package com.moscow.cineverse.screen.home

interface HomeInteractionListener {
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onSeeAllClick(type: HomeFeaturedItems)
    fun onCollectionsShowMoreClick()
    fun onCollectionClick(collectionId: Int, collectionName: String)
    fun onPromotionClick(promotionId: Int)
    fun onWatchSuggestionClick()
    fun onBrowseSuggestionClick()
    fun onRefresh()
    fun onSeeMoreRecentlyViewedClicked()
    fun onFeaturedCollectionClick(genreId: Int)
}