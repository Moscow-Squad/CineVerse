package com.moscow.cineverse.screen.series_details

sealed interface SeriesDetailsScreenEffects {
    data class AddToCollection(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToRecommendationSeries(val seriesId: Int, val seriesName: String) : SeriesDetailsScreenEffects
    data class NavigateToReviewsScreen(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToSeriesSeasonsScreen(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToSeriesDetailsScreen(val seriesId: Int) : SeriesDetailsScreenEffects
    data class NavigateToActorDetailsScreen(val ActorId: Int) : SeriesDetailsScreenEffects
    data class OpenTrailer(val url: String) : SeriesDetailsScreenEffects
}