package com.moscow.cineverse.screen.match

import androidx.lifecycle.ViewModel
import com.moscow.cineverse.base.BaseViewModel

class MatchViewModel : BaseViewModel<MatchUiState, MatchEvent>(MatchUiState()),
    MatchInteractionListener {
    override fun onClickStartMatching() {
        sendEvent(MatchEvent.OnClickStartMatching)
    }

}