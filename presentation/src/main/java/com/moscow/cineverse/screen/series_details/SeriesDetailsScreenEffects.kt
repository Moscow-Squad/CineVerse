package com.moscow.cineverse.screen.series_details

sealed interface SeriesDetailsScreenEffects {
    data class AddToCollection(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToRecommendationSeries(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToReviewsScreen(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToSeriesSeasonsScreen(val seriesId: Int) : SeriesDetailsScreenEffects
}