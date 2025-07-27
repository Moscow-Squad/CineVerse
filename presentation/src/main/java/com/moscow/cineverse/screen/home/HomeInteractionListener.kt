package com.moscow.cineverse.screen.home

interface HomeInteractionListener {
    fun onSeriesClick(seriesId: Int)
    fun onMovieClick(movieId: Int)
    fun onSeeAllClick(type: HomeFeaturedItems)
    fun onCollectionsShowMoreClick()
    fun onCollectionClick(collectionId: Int)
    fun onPromotionClick(promotionId: Int)
    fun onWatchSuggestionClick()
    fun onBrowseSuggestionClick()
    fun onRefresh()
}