package com.moscow.cineverse.screen.history

import com.moscow.cineverse.screen.home.HomeEvent

sealed class HistoryEffect {
    data object NavigateBack : HistoryEffect()
    data class MovieClicked(val movieId: Int): HistoryEffect()
    data class SeriesClicked(val seriesId: Int): HistoryEffect()
}