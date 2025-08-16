package com.moscow.cineverse.screen.details.series_details

import com.moscow.cineverse.utlis.ViewMode

interface SeriesDetailsInteractionListener {
    fun addToCollection()
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onDismissLoginBottomSheet()
    fun navigateToLogin()
    fun onRatingSubmit(rating: Int)
    fun onDeleteRatingSeries()
    fun onShowMoreRecommendationsClicked()
    fun onShowMoreReviewsClicked()
    fun onShowMoreSeasonsClicked()
    fun onViewModeChanged(viewMode: ViewMode)
    fun onSeriesClicked(seriesId: Int)
    fun onActorClicked(actorId: Int)
    fun onPlayButtonClicked()
    fun onRetry()
}