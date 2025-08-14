package com.moscow.cineverse.screen.match

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        if (uiState.value.isNextButtonActivated)
            updateState { state ->
                val nextIndex = state.currentQuestionType.ordinal + 1
                QuestionType.entries.getOrNull(nextIndex)
                    ?: run {
                        loadMatchData()
                        return@updateState state.copy(isLoadingRecommendations = true)
                    }

                state.copy(currentQuestionType = QuestionType.entries[nextIndex])

            }
    }

    private fun loadMatchData() = viewModelScope.launch {
        delay(2000)
        updateState {
            it.copy(
                isLoadingRecommendations = false,
                currentPage = MatchPages.ResultsPage
            )
        }

    }

    override fun onAnswerSelected(type: QuestionType, answer: QuestionUiState) {
        updateState { state ->
            when (type) {
                QuestionType.MOOD -> state.copy(
                    moodQuestions = state.moodQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else it
                    }
                )

                QuestionType.GENRE -> state.copy(
                    genreQuestions = state.genreQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else it
                    }
                )

                QuestionType.TIME -> state.copy(
                    timeQuestions = state.timeQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else
                            it.copy(isSelected = false)
                    }
                )

                QuestionType.TYPE -> state.copy(
                    movieTypeQuestions = state.movieTypeQuestions.map {
                        if (it.id == answer.id) it.copy(isSelected = !it.isSelected) else
                            it.copy(isSelected = false)
                    }
                )
            }
        }
    }

    override fun onNavigateBack() {
        when (uiState.value.currentPage) {
            MatchPages.QuestionsPage -> {
                updateState { state ->
                    state.copy(
                        currentPage = if (state.currentQuestionType == QuestionType.MOOD)
                            MatchPages.StartPage
                        else
                            MatchPages.QuestionsPage,
                        currentQuestionType = QuestionType.entries[state.currentQuestionType.ordinal.minus(
                            1
                        ).coerceAtLeast(0)]
                    )
                }
            }

            MatchPages.ResultsPage -> updateState {
                it.copy(currentPage = MatchPages.QuestionsPage)
            }

            else -> {}
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