package com.moscow.cineverse.screen.series_details

import com.moscow.cineverse.designSystem.component.ViewMode

interface SeriesInteractionListener {
    fun addToCollection()
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onRatingSubmit(rating: Int, seriesId: Int)
    fun onShowMoreRecommendationsClicked(seriesId: Int)
    fun onShowMoreReviewsClicked(seriesId: Int)
    fun onViewModeChanged(viewMode: ViewMode)
}