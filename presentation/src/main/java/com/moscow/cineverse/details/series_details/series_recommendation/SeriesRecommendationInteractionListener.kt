package com.moscow.cineverse.details.series_details.series_recommendation

import com.moscow.cineverse.utlis.ViewMode

interface SeriesRecommendationInteractionListener {
    fun onSeriesClicked(seriesId: Int)
    fun onViewModeChanged(viewMode: ViewMode)
}