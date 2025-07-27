package com.moscow.cineverse.screen.home

sealed class HomeEvent {
    data class MovieClicked(val movieId: Int): HomeEvent()
    data class SeriesClicked(val seriesId: Int): HomeEvent()
    data class SeeAllClicked(val category: String): HomeEvent()
    data class CollectionClicked(val collectionId: Int): HomeEvent()
    data class PromotionClicked(val promotionId: Int): HomeEvent()
    object BrowseSuggestionClicked : HomeEvent()
    object WatchingSuggestionClicked : HomeEvent()
}