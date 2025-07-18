package com.moscow.cineverse.screen.series_details

sealed interface SeriesDetailsEvents {
    data class AddToCollection(val seriesId: Int) : SeriesDetailsEvents
}