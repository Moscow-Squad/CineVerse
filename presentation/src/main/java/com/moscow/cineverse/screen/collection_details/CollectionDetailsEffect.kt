package com.moscow.cineverse.screen.collection_details

sealed class CollectionDetailsEffect {
    data object NavigateBack: CollectionDetailsEffect()
    data class NavigateToSeriesDetails(val seriesId: Int): CollectionDetailsEffect()
    data class NavigateToMovieDetails(val movieId: Int): CollectionDetailsEffect()
}