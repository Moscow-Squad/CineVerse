package com.moscow.cineverse.screen.series_details

import com.moscow.cineverse.designSystem.component.ViewMode

interface SeriesDetailsScreenInteractionListener {
    fun addToCollection()
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onRatingSubmit(rating: Int, seriesId: Int)
    fun onShowMoreRecommendationsClicked(seriesId: Int)
    fun onShowMoreReviewsClicked(seriesId: Int)
    fun onShowMoreSeasonsClicked(seriesId: Int)
    fun onViewModeChanged(viewMode: ViewMode)
    fun onSeriesClicked(seriesId: Int)
    fun onActorClicked(actorId: Int)
}