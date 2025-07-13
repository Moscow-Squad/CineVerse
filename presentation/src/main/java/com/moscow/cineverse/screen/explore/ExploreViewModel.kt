package com.moscow.cineverse.screen.explore

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.GetMovieByGenreIdUseCase
import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.android.domain.usecase.SearchUseCase
import com.android.domain.usecase.SuggestionUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    val getLocalSuggestions: GetLocalSuggestions,
    val genreUseCase: GenreUseCase,
    private val getMovieByGenreIdUseCase: GetMovieByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
) : BaseViewModel<ExploreScreenState, ExploreScreenEvents>(ExploreScreenState()),
    ExploreInteractionListener {

    init {
        loadMovies()
        loadSeries()
        observeKeyword()
    }

    override fun getSavedHistoryItems(suggestion: String) {
        TODO("Not yet implemented")
    }

    override fun searchMovie(isHistory: Boolean) {
        launchWithFlow(
            flowAction = { searchUseCase.searchMovie(uiState.value.searchKeyWord) },
            onSuccess = ::onMovieSearchSuccess,
            onError = ::onMovieSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally,
        )
    }

    override fun searchSeries(isHistory: Boolean) {
        launchWithFlow(
            flowAction = { searchUseCase.searchSeries(uiState.value.searchKeyWord) },
            onSuccess = ::onSeriesSearchSuccess,
            onError = ::onSeriesSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    override fun searchActor(isHistory: Boolean) {
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
                searchResult = it.searchResult.plus(it.selectedTab.toTitle() to items.map {
                    it.toUi(
                        uiState.value.moviesGenres
                    )
                }),
                isLoading = false
            )
        }
        updateContentList()
    }

    private fun onSeriesSearchSuccess(items: List<Series>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(it.selectedTab.toTitle() to items.map {
                    it.toUi(
                        uiState.value.seriesGenres
                    )
                }),
                isLoading = false
            )
        }
        updateContentList()
    }

    private fun onActorSearchSuccess(items: List<Actor>) {
        updateState { state ->
            state.copy(
                actorsSearchResult = state.actorsSearchResult + items.map { actor -> actor.toUi() },
                isLoading = false
            )
        }
        updateContentList()
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
        launchWithFlow(
            flowAction = { getLocalSuggestions.localSuggestions() },
            onSuccess = { history ->
                val suggestions = history.map { SuggestItemUiState(it, isHistory = true) }
                updateState { it.copy(localSuggestions = suggestions) }
            },
            onError = {},
            onStart = {},
            onFinally = {
                if (uiState.value.localSuggestions.isNotEmpty()) {
                    updateState { it.copy(showHistory = true) }
                }
            }
        )
    }

    override fun onCancelButtonClicked() {
        updateState {
            it.copy(
                searchKeyWord = "",
                showHistory = false,
                showSuggestions = false,
                remoteSuggestions = emptyList(),
                searchResult = mutableMapOf()
            )
        }
        updateContentList()
    }

    override fun onSearchValueChange(text: String) {
        updateState {
            it.copy(
                searchKeyWord = text,
                showSuggestions = true,
            )
        }
    }
    override fun onSearchWordDetected(searchKeyWord: List<String>) {
        updateState {
            it.copy(
                searchKeyWord = searchKeyWord[0],
                showSuggestions = true,
            )
        }
    }

    override fun SuggestionList(): List<SuggestItemUiState> {
        return (uiState.value.localSuggestions.filter {
            it.title.contains(
                uiState.value.searchKeyWord,
                ignoreCase = true
            )
        } + uiState.value.remoteSuggestions.map { SuggestItemUiState(it, isHistory = false) })
    }

    override fun onClickSuggestion(suggestion: SuggestItemUiState) {
        updateState {
            it.copy(
                searchKeyWord = suggestion.title,
            )
        }
        if (suggestion.isHistory) {
            searchMovie(isHistory = true)
        } else {
            searchMovie()
        }
        updateState {
            it.copy(
                showHistory = false,
                showSuggestions = false,
                remoteSuggestions = emptyList()
            )
        }
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

    override fun getMoviesByGenreId(genreId: Int) {
        launchWithFlow(
            flowAction = { getMovieByGenreIdUseCase.getMovieByGenreId(genreId) },
            onSuccess = ::onGetMovieByGenreIdSuccess,
            onError = ::onGetMovieByGenreIdFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetMovieByGenreIdFailed(e: Throwable) {

    }

    private fun onGetMovieByGenreIdSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                movies = movies.map { movie -> movie.toUi(uiState.value.moviesGenres) }
            )
        }
        updateContentList()
    }

    override fun getSeriesByGenreId(genreId: Int) {
        launchWithFlow(
            flowAction = { getSeriesByGenreIdUseCase.getSeriesByGenreId(genreId) },
            onSuccess = ::onGetSeriesByGenreIdSuccess,
            onError = ::onGetSeriesByGenreIdFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetSeriesByGenreIdSuccess(series: List<Series>) {
        updateState {
            it.copy(
                series = series.map { movie -> movie.toUi(uiState.value.seriesGenres) }

            )
        }
        updateContentList()
        updateContentList()
    }

    private fun onGetSeriesByGenreIdFailed(e: Throwable) {

    }


    override fun onGenreSelected(genreId: Int) {
        updateState { it.copy(selectedGenre = genreId) }

        when (uiState.value.selectedTab) {
            ExploreTabsPages.MOVIES -> {
                if (genreId == 0)
                    loadMovies()
                else
                    getMoviesByGenreId(genreId)
            }

            ExploreTabsPages.SERIES ->
                if (genreId == 0)
                    loadSeries()
                else
                    getSeriesByGenreId(genreId)

            ExploreTabsPages.ACTORS -> {}
        }

    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState { it.copy(viewMode = viewMode) }
    }

    override fun onMovieClick(movieId: Int) {
        sendEvent(ExploreScreenEvents.MovieClicked(movieId))
    }

    override fun onTabSelected(tab: ExploreTabsPages) {
        updateState {
            it.copy(
                selectedTab = tab, contentList = when (tab) {
                    ExploreTabsPages.MOVIES -> it.movies
                    ExploreTabsPages.SERIES -> it.series
                    ExploreTabsPages.ACTORS -> it.movies
                },
                genres = when (tab) {
                    ExploreTabsPages.MOVIES -> it.moviesGenres
                    ExploreTabsPages.SERIES -> it.seriesGenres
                    ExploreTabsPages.ACTORS -> it.moviesGenres
                }
            )
        }
    }

    override fun onRefresh() {
    }

    private fun loadSeries() {
        viewModelScope.launch {
            combine(
                flow = genreUseCase.getSeriesGenres(),
                flow2 = getSeriesUseCase(),
                transform = { seriesGenres, series ->
                    Pair(seriesGenres, series)
                }
            ).collect { result ->
                updateState {
                    it.copy(
                        seriesGenres = result.first.map { it.toUi() },
                        series = result.second.map { series -> series.toUi(result.first.map { it.toUi() }) },
                    )
                }
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            combine(
                flow = genreUseCase.getMoviesGenres(),
                flow2 = getMoviesUseCase(),
                transform = { movieGenres, movies ->
                    Pair(movieGenres, movies)
                }
            ).collect { result ->
                updateState {
                    it.copy(
                        moviesGenres = result.first.map { it.toUi() },
                        movies = result.second.map { movie -> movie.toUi(result.first.map { it.toUi() }) },
                    )
                }
                if (uiState.value.contentList.isEmpty()) {
                    updateState { it.copy(contentList = it.movies, selectedGenre = 0) }
                }
                if (uiState.value.genres.isEmpty()) {
                    updateState { it.copy(genres = it.moviesGenres, selectedGenre = 0) }
                }
            }
        }
    }

    private fun updateContentList() {
        if (uiState.value.searchResult.isNotEmpty())
            updateState {
                it.copy(
                    contentList = it.searchResult.getOrDefault(
                        it.selectedTab.toTitle(),
                        emptyList()
                    ),
                    shouldShowGenres = false
                )
            }
        else {
            updateState {
                it.copy(
                    shouldShowGenres = true,
                    contentList = when (it.selectedTab) {
                        ExploreTabsPages.MOVIES -> it.movies
                        ExploreTabsPages.SERIES -> it.series
                        ExploreTabsPages.ACTORS -> it.movies
                    },
                )
            }
        }
    }

}