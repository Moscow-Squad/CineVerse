package com.moscow.cineverse.screen.history

sealed class HistoryEffect {
    data object NavigateBack : HistoryEffect()
    data class MovieClicked(val movieId: Int): HistoryEffect()
    data class SeriesClicked(val seriesId: Int): HistoryEffect()
    data object WatchSomethingButtonClicked: HistoryEffect()
}