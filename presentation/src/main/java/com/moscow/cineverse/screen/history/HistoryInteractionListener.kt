package com.moscow.cineverse.screen.history

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.domain.model.MediaType

interface HistoryInteractionListener {
    fun onBackPressed()
    fun onMediaItemClicked(mediaItemUiState: MediaItemUiState)
    fun onTipCancelIconClicked()
    fun onItemDeletedIconClicked(mediaId: Int)
}