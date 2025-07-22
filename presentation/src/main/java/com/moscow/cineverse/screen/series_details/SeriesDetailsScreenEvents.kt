package com.moscow.cineverse.screen.series_details

sealed interface SeriesDetailsScreenEvents {
    data class AddToCollection(val seriesId: Int) : SeriesDetailsScreenEvents
    data class NavigateToRecommendationSeries(val seriesId: Int) : SeriesDetailsScreenEvents
    data class NavigateToReviewsScreen(val seriesId: Int) : SeriesDetailsScreenEvents
    data class NavigateToSeriesSeasonsScreen(val seriesId: Int) : SeriesDetailsScreenEvents
}