package com.moscow.cineverse.screen.match

import com.moscow.cineverse.base.BaseViewModel
import javax.inject.Inject

class MatchViewModel @Inject constructor(

) : BaseViewModel<MatchUiState, MatchEvent>(MatchUiState()),
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
        when (uiState.value.currentPage) {
            MatchPages.QuestionsPage -> updateState {
                it.copy(currentPage = MatchPages.StartPage)
            }
            MatchPages.ResultsPage -> updateState {
                it.copy(currentPage = MatchPages.QuestionsPage)
            }
            else -> {} // No action needed for StartPage
        }
    }

    override fun onMovieClick(id: Int) {
        sendEvent(MatchEvent.OnMovieClick(id = id))
    }

    override fun onSaveClick(id: Int) {
        sendEvent(MatchEvent.AddToCollection(id = id))
    }

    override fun onPlayClick(url: String) {
        sendEvent(MatchEvent.OpenTrailer(url = url))
    }

}