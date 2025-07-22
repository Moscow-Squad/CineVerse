package com.moscow.cineverse.screen.series_details

interface SeriesInteractionListener {
    fun addToCollection()
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onRatingSubmit(rating: Int, seriesId: Int)
    fun onShowMoreRecommendationsClicked(seriesId: Int, seriesTitle: String)
    fun onShowMoreReviewsClicked(seriesId: Int)
}