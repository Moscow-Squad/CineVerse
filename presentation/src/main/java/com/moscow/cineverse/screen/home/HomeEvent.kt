package com.moscow.cineverse.screen.home

sealed class HomeEvent {
    data class MovieClicked(val movieId: Int): HomeEvent()
    data class SeriesClicked(val seriesId: Int): HomeEvent()
    data class SeeAllClicked(val category: HomeFeaturedItems): HomeEvent()
    data class CollectionClicked(
        val collectionId: Int,
        val collectionName: String
    ): HomeEvent()
    data class PromotionClicked(val promotionId: Int): HomeEvent()
    object BrowseSuggestionClicked : HomeEvent()
    object WatchingSuggestionClicked : HomeEvent()
    object SeeMoreRecentlyViewed: HomeEvent()
    object SeeMoreCollections: HomeEvent()
}