package com.moscow.cineverse.screen.history

interface HistoryInteractionListener {
    fun onBackPressed()
    fun onRefresh()
    fun onMediaItemClicked()
}