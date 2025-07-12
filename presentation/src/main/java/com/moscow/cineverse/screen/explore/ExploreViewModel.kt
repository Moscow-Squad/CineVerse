package com.moscow.cineverse.screen.explore

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.SearchUseCase
import com.android.domain.usecase.SuggestionUseCase
import com.moscow.cineverse.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val searchUseCase: SearchUseCase,
    private val suggestionUseCase: SuggestionUseCase,
    val getLocalSuggestions: GetLocalSuggestions,
    val genreUseCase: GenreUseCase
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
                searchResult = it.searchResult.plus(it.selectedTab!! to items.map { it.toUi(uiState.value.moviesGenres) }),
                isLoading = false
            )
        }
    }

    private fun onSeriesSearchSuccess(items: List<Series>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(it.selectedTab!! to items.map { it.toUi(uiState.value.seriesGenres) }),
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

    init {
        observeKeyword()
    }

    private fun observeKeyword() {
        uiState
            .map { it.searchKeyWord }
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { keyword ->
                getSuggestions(keyword)
                updateState { it.copy(showHistory = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun getSuggestions(keyword: String, page: Int = 1) {
        launchWithResult(
            action = { suggestionUseCase.getSuggestions(keyword, page) },
            onSuccess = ::onSuccessLoadingSuggestions,
            onError = { },
            onStart = { },
            onFinally = { }
        )
    }

    private fun onSuccessLoadingSuggestions(suggestion: Flow<List<String>>) {
        viewModelScope.launch {
            suggestion.collect { s ->
                updateState { it.copy(remoteSuggestions = s) }
            }
        }
    }

    override fun onSearchBarClickedOn() {
        updateState { it.copy(isSearchBarClickedOn = true, showSuggestions = true) }
        getHistoryData()
    }

    private fun getHistoryData() {
        launchWithResult<List<String>>(
            action = { getLocalSuggestions() },
            onSuccess = { history ->
                val suggestions = history.map { SuggestItemUiState(it, isHistory = true) }
                updateState { it.copy(localSuggestions = suggestions) }
            },
            onError = {},
            onStart = {},
            onFinally = { updateState { it.copy(showHistory = true) } }
        )
    }

    override fun onCancelButtonClicked() {
        updateState { it.copy(searchKeyWord = "", showHistory = false, showSuggestions = false) }
    }

    override fun onSearchValueChange(text: String) {
        updateState { it.copy(searchKeyWord = text) }
    }

    override fun SuggestionList(): List<SuggestItemUiState> {
        return (uiState.value.localSuggestions.filter {
            it.title.contains(
                uiState.value.searchKeyWord,
                ignoreCase = true
            )
        }
                + uiState.value.remoteSuggestions.map { SuggestItemUiState(it, isHistory = false) })
    }

    override fun onClickSuggestion(text: String) {
        Log.d("dddddddddddddddd", text)
    }

    override fun clearAllLocalSuggestions() {
        Log.d("dddddddddddddddd", "Clear allllllllllllllllll")
    }

    override fun getMoviesGenres() {
        launchWithFlow(
            flowAction = { genreUseCase.getMoviesGenres() },
            onSuccess = ::onMoviesGenresSuccess,
            onError = ::onMoviesGenresFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(moviesGenres = genres.map { it.toUi() }) }
    }

    private fun onMoviesGenresFailed(e: Throwable) {

    }


    override fun getSeriesGenres() {
        launchWithFlow(
            flowAction = { genreUseCase.getSeriesGenres() },
            onSuccess = ::onSeriesGenresSuccess,
            onError = ::onSeriesGenresFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    override fun onKeyboardClick() {
        getSuggestions(uiState.value.searchKeyWord)
    }

    private fun onSeriesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(seriesGenres = genres.map { it.toUi() }) }
    }

    private fun onSeriesGenresFailed(e: Throwable) {

    }

}