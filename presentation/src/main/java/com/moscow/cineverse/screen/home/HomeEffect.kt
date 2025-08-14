package com.moscow.cineverse.screen.home

sealed class HomeEffect {
    data class MovieClicked(val movieId: Int): HomeEffect()
    data class SeriesClicked(val seriesId: Int): HomeEffect()
    data class SeeAllClicked(val category: HomeFeaturedItems): HomeEffect()
    data class CollectionClicked(val collectionId: Int, val collectionName: String): HomeEffect()
    data class FeaturedCollectionClicked(val genreId: Int, val collectionName: Int): HomeEffect()
    object BrowseSuggestionClicked : HomeEffect()
    object WatchingSuggestionClicked : HomeEffect()
    object SeeMoreRecentlyViewed: HomeEffect()
    object SeeMoreCollections: HomeEffect()
}