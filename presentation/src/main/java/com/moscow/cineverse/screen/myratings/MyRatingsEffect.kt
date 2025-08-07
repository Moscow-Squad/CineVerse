package com.moscow.cineverse.screen.myratings

sealed class MyRatingsEffect {
    data class MovieClicked(val movieId: Int) : MyRatingsEffect()
    data class SeriesClicked(val seriesId: Int) : MyRatingsEffect()
    object NavigateBack : MyRatingsEffect()
    object NavigateToExplore : MyRatingsEffect()
}