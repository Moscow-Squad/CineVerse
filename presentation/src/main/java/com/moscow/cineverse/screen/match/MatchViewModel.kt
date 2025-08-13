package com.moscow.cineverse.screen.match

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.domain.model.MediaType

class MatchViewModel : BaseViewModel<MatchUiState, MatchEvent>(MatchUiState()),
    MatchInteractionListener {

    override fun onClickStartMatching() {
        updateState { it.copy(currentPage = MatchPages.QuestionsPage) }
    }

    override fun onClickFinishMatching() {
        updateState { it.copy(currentPage = MatchPages.ResultsPage) }
    }

    override fun onClickNextQuestion() {
        updateState { state ->
            val nextIndex = state.currentQuestionIndex + 1
            if (nextIndex < state.moodQuestions.size) {
                state.copy(currentQuestionIndex = nextIndex)
            } else {
                state.copy(currentPage = MatchPages.ResultsPage)
            }
        }
    }

    override fun onAnswerSelected(questionIndex: Int, answer: String) {

    }

    override fun onNavigateBack() {

    }

    override fun onMediaItemClick(id: Int, type: MediaType) {
        sendEvent(MatchEvent.OnMediaItemClick(id = id, type = type))
    }

}