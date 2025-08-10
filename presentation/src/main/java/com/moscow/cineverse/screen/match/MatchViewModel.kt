package com.moscow.cineverse.screen.match

import com.moscow.cineverse.base.BaseViewModel

class MatchViewModel : BaseViewModel<MatchUiState, MatchEvent>(MatchUiState()),
    MatchInteractionListener {

    override fun onClickStartMatching() {
        updateState { it.copy(currentPage = MatchPages.QuestionsPage) }
    }

    override fun onClickFinishMatching() {
        updateState { it.copy(currentPage = MatchPages.ResultsPage) }
    }

}