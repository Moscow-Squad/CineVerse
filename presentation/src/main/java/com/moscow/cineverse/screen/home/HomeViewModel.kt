package com.moscow.cineverse.screen.home

import androidx.lifecycle.viewModelScope


import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.mapper.toGenreUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.domain.model.Genre
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.home.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.home.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.home.GetTopRatedTVShowsUseCase
import com.moscow.domain.usecase.home.GetTrendingMoviesUseCase
import com.moscow.domain.usecase.home.GetUpcomingMoviesUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class HomeViewModel(
    private val getMatchesYourVibesMoviesUseCase: GetMatchesYourVibesMoviesUseCase,
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val genreUseCase: GenreUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()), HomeInteractionListener {

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            getGenres()
            coroutineScope {
                launch { fetchTrendingMovies() }
                launch { fetchRecentlyReleasedMovies() }
                launch { fetchUpcomingMovies() }
                launch { fetchTopRatedTVShows() }
                launch { fetchMatchesYourVibesMovies() }
            }
        }

    }

    private suspend fun getGenres() {
        return suspendCancellableCoroutine { continuation ->
            launchWithResult(
                action = { genreUseCase.getMoviesGenres() },
                onSuccess = {
                    onGetGenresSuccess(it)
                    continuation.resume(
                        value = Unit,
                    ) { cause, value, context ->

                    }
                },
                onError = {
                    onGetGenresError(it)
                    continuation.resume(
                        value = Unit,
                    ) { cause, value, context ->

                    }
                },
            )
        }
    }

    private fun onGetGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                genres = genres.map { genre -> genre.toGenreUi() },
                isLoading = false,
            )
        }
    }

    private fun onGetGenresError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
    }

    private fun fetchTrendingMovies() {
        launchWithResult(
            action = {
                getTrendingMoviesUseCase()
            },
            onSuccess = ::onFetchTrendingMoviesSuccess,
            onError = ::onFetchTrendingMoviesError,
        )
    }

    private fun onFetchTrendingMoviesSuccess(movies: List<Movie>) {
        updateState { it.copy(sliderItems = movies.toUi(uiState.value.genres), isLoading = false) }
    }

    private fun onFetchTrendingMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message, isLoading = false) }
    }

    private fun fetchRecentlyReleasedMovies() {
        launchWithResult(
            action = { getRecentlyReleasedMoviesUseCase(page = 1) },
            onSuccess = ::onFetchRecentlyReleasedMoviesSuccess,
            onError = ::onFetchRecentlyReleasedMoviesError,
        )
    }

    private fun onFetchRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                recentlyReleasedMovies = movies.toUi(uiState.value.genres),
                isLoading = false
            )
        }
    }

    private fun onFetchRecentlyReleasedMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message, isLoading = false) }
    }

    private fun fetchUpcomingMovies() {
        launchWithResult(
            action = { getUpcomingMoviesUseCase(page = 1) },
            onSuccess = ::onFetchUpcomingMoviesSuccess,
            onError = ::onFetchUpcomingMoviesError,
        )
    }

    private fun onFetchUpcomingMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                upcomingMovies = movies.toUi(uiState.value.genres),
                isLoading = false
            )
        }
    }

    private fun onFetchUpcomingMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message, isLoading = false) }
    }

    private fun fetchTopRatedTVShows() {
        launchWithResult(
            action = { getTopRatedTVShowsUseCase(page = 1) },
            onSuccess = ::onFetchTopRatedTVShowsSuccess,
            onError = ::onFetchTopRatedTVShowsError,
        )
    }

    private fun onFetchTopRatedTVShowsSuccess(tvShows: List<Series>) {
        updateState { it.copy(topRatedTvShows = tvShows.toUi(), isLoading = false) }
    }

    private fun onFetchTopRatedTVShowsError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message, isLoading = false) }
    }

    private fun fetchMatchesYourVibesMovies() {

        launchWithResult(
            action = { getMatchesYourVibesMoviesUseCase(genreId = 28, page = 1) },
            onSuccess = ::onFetchMatchesYourVibesMoviesSuccess,
            onError = ::onFetchMatchesYourVibesMoviesError,
        )
    }

    private fun onFetchMatchesYourVibesMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                matchesYourVibe = movies.toUi(uiState.value.genres),
                isLoading = false,
                error = null
            )
        }
    }

    private fun onFetchMatchesYourVibesMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message, isLoading = false) }
    }


    override fun onMediaItemClicked(mediaItemUiState: MediaItemUiState) {
        if (mediaItemUiState.mediaType == MediaType.Movie)
            sendEvent(HomeEvent.MovieClicked(mediaItemUiState.id))
        else
            sendEvent(HomeEvent.SeriesClicked(mediaItemUiState.id))
    }



    override fun onSeeAllClick(type: HomeFeaturedItems) {
        sendEvent(HomeEvent.SeeAllClicked(type))
    }

    override fun onCollectionsShowMoreClick() {
        TODO("Not yet implemented")
    }

    override fun onCollectionClick(collectionId: Int) {
        sendEvent(HomeEvent.CollectionClicked(collectionId))
    }

    override fun onPromotionClick(promotionId: Int) {
        sendEvent(HomeEvent.PromotionClicked(promotionId))
    }

    override fun onWatchSuggestionClick() {
        sendEvent(HomeEvent.WatchingSuggestionClicked)
    }

    override fun onBrowseSuggestionClick() {
        sendEvent(HomeEvent.BrowseSuggestionClicked)
    }

    override fun onRefresh() {
        loadHomeData()
    }


}