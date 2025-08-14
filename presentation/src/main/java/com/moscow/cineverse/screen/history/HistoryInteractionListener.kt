package com.moscow.cineverse.screen.history

interface HistoryInteractionListener {
    fun onBackPressed()
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onTipCancelIconClicked()
    fun onItemDeletedIconClicked(mediaId: Int)
    fun onFindToSomethingToWatchButton()
    fun onRetry()
}