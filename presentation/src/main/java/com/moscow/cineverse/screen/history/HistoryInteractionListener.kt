package com.moscow.cineverse.screen.history

import com.moscow.cineverse.common_ui_state.MediaItemUiState

interface HistoryInteractionListener {
    fun onBackPressed()
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onTipCancelIconClicked()
    fun onItemDeletedIconClicked(mediaId: Int)
    fun onFindToSomethingToWatchButton()
    fun onRetry()
}