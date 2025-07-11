package com.moscow.cineverse.screen.explore

import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.usecase.SearchUseCase
import androidx.lifecycle.viewModelScope
import com.android.domain.model.Suggestion
import com.android.domain.usecase.SuggestionUseCase
import com.moscow.cineverse.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExploreViewModel(
    private val searchUseCase: SearchUseCase,
    private val suggestionUseCase: SuggestionUseCase
) : BaseViewModel<ExploreScreenState, ExploreScreenEvents>(ExploreScreenState()),
    ExploreInteractionListener {

    override fun getSavedHistoryItems(suggestion: String) {
        TODO("Not yet implemented")
    }

    override fun searchMovie() {
        launchWithFlow(
            flowAction = { searchUseCase.searchMovie(uiState.value.searchKeyWord) },
            onSuccess = ::onMovieSearchSuccess,
            onError = ::onMovieSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally,
        )
    }

    override fun searchSeries() {
        launchWithFlow(
            flowAction = { searchUseCase.searchSeries(uiState.value.searchKeyWord) },
            onSuccess = ::onSeriesSearchSuccess,
            onError = ::onSeriesSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    override fun searchActor() {
        launchWithFlow(
            flowAction = { searchUseCase.searchActor(uiState.value.searchKeyWord) },
            onSuccess = ::onActorSearchSuccess,
            onError = ::onActorsSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onMovieSearchSuccess(items: List<Movie>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(it.selectedTab!! to items.map { it.toUi() }),
                isLoading = false
            )
        }
    }

    private fun onSeriesSearchSuccess(items: List<Series>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(it.selectedTab!! to items.map { it.toUi() }),
                isLoading = false
            )
        }
    }

    private fun onActorSearchSuccess(items: List<Actor>) {
        updateState { state ->
            state.copy(
                actorsSearchResult = state.actorsSearchResult + items.map { actor -> actor.toUi() },
                isLoading = false
            )
        }
    }

    private fun onMovieSearchFailed(e: Throwable) {
    }

    private fun onSeriesSearchFailed(e: Throwable) {
    }

    private fun onActorsSearchFailed(e: Throwable) {
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }

    private val keywordFlow = MutableStateFlow(uiState.value.searchKeyWord)

    init {
        observeKeyword()
    }

    fun onKeywordChanged(newKeyword: String) {
        keywordFlow.value = newKeyword
    }

    private fun observeKeyword() {
        keywordFlow
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { keyword ->
                getSuggestions(keyword)
            }
            .launchIn(viewModelScope)
    }

    private fun getSuggestions(keyword: String, page: Int = 1) {
        launchWithResult(
            action = { suggestionUseCase.getSuggestions(keyword, page).first() },
            onSuccess = ::onSuccessLoadingSuggestions,
            onError = { },
            onStart = { },
            onFinally = { }
        )
    }

    private fun onSuccessLoadingSuggestions(suggestion: List<Suggestion>) {

        updateState {
            it.copy(
                suggestions = suggestion
            )
        }
    }
}