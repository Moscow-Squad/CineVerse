package com.moscow.cineverse.screen.history

sealed class HistoryEffect {
    data object NavigateBack : HistoryEffect()
}