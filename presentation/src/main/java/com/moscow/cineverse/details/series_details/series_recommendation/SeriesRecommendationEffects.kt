package com.moscow.cineverse.details.series_details.series_recommendation

sealed interface SeriesRecommendationEffects {
    data class NavigateToSeriesDetailsScreen(val seriesId: Int) : SeriesRecommendationEffects
}