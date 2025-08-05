package com.moscow.cineverse.screen.home


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.mapper.toGenreUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.domain.model.Genre
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.model.UserType
import com.moscow.domain.usecase.collection.GetCollectionDetailsUseCase
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.home.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.home.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.home.GetTopRatedTVShowsUseCase
import com.moscow.domain.usecase.home.GetTrendingMoviesUseCase
import com.moscow.domain.usecase.home.GetUpcomingMoviesUseCase
import com.moscow.domain.usecase.local.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMatchesYourVibesMoviesUseCase: GetMatchesYourVibesMoviesUseCase,
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val genreUseCase: GenreUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getCollectionDetailsUseCase: GetCollectionDetailsUseCase,
    private val getUserCollectionsUseCase: GetUserCollectionsUseCase
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()), HomeInteractionListener {

    init {
        updateState { it.copy(isLoading = true) }
        loadHomeData()
    }

    private fun loadHomeData() {
        updateState { it.copy(isLoading = true, error = null) }
        launchAndForget(
            action = {
                viewModelScope.launch {
                    getGenres()
                    coroutineScope {
                        val jobs = listOf(
                            launch { getUserDetails() },
                            launch { fetchTrendingMovies() },
                            launch { fetchRecentlyReleasedMovies() },
                            launch { fetchUpcomingMovies() },
                            launch { fetchTopRatedTVShows() },
                            launch { fetchMatchesYourVibesMovies() },
                            launch { getUserCollection() }
                        )
                        jobs.forEach { it.join() }
                    }
                    waitUntilAllDataIsReady()
                    updateState { it.copy(isLoading = false) }
                }
            },
            onError = { e ->
                updateState { it.copy(isLoading = false, error = e.message) }
            }
        )
    }

    private suspend fun waitUntilAllDataIsReady() {
        var wait = 0
        while (uiState.value.sliderItems.isEmpty()
            || uiState.value.recentlyReleasedMovies.isEmpty()
            || uiState.value.upcomingMovies.isEmpty()
            || uiState.value.topRatedTvShows.isEmpty()
            || uiState.value.matchesYourVibe.isEmpty()
        ) {
            wait++
            if (wait == 25) {
                updateState { it.copy(isLoading = false, error = "error loading") }
                return
            }
            delay(100)
        }
    }

    private fun getUserCollection() {
        launchWithResult(
            action = { getUserCollectionsUseCase(1) },
            onSuccess = { collections ->
                Log.d("sssssssssssssssssss", collections.toString())
                updateState { it.copy(collections = collections.map { it.toUi() }) }
            },
            onError = {e ->
                updateState { it.copy(isLoading = false, error = e.message) }
            },
        )
    }

    private fun getUserDetails() {
        launchWithResult(
            action = getUserDetailsUseCase::invoke,
            onSuccess = ::onGetUserDetailsSuccess,
            onError = ::onGetUserDetailsError
        )
    }

    private fun onGetUserDetailsSuccess(user: UserType) {
        when (user) {
            is UserType.AuthenticatedUser -> {
                updateState {
                    it.copy(
                        userName = user.username,
                        recentlyCollectionId = user.recentlyCollectionId
                    )
                }
                getRecentlyViewedMovies(user.recentlyCollectionId)
            }

            is UserType.GuestUser -> {
                updateState { it.copy(userName = null) }
            }
        }
    }

    private fun onGetUserDetailsError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
    }

    fun getRecentlyViewedMovies(recentlyCollectionId: Int) {
        launchWithResult(
            action = { getCollectionDetailsUseCase(recentlyCollectionId, 1) },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        youRecentlyViewed = result.reversed().toMediaItemUiState()
                    )
                }
            },
            onError = {}
        )
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
                genres = genres.map { genre -> genre.toGenreUi() }
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
        updateState { it.copy(sliderItems = movies.toUi(uiState.value.genres)) }
    }

    private fun onFetchTrendingMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
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

                )
        }
    }

    private fun onFetchRecentlyReleasedMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
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

                )
        }
    }

    private fun onFetchUpcomingMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
    }

    private fun fetchTopRatedTVShows() {
        launchWithResult(
            action = { getTopRatedTVShowsUseCase(page = 1) },
            onSuccess = ::onFetchTopRatedTVShowsSuccess,
            onError = ::onFetchTopRatedTVShowsError,
        )
    }

    private fun onFetchTopRatedTVShowsSuccess(tvShows: List<Series>) {
        updateState { it.copy(topRatedTvShows = tvShows.toUi()) }
    }

    private fun onFetchTopRatedTVShowsError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
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
                error = null
            )
        }
    }

    private fun onFetchMatchesYourVibesMoviesError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
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
    }

    override fun onCollectionClick(collectionId: Int, collectionName: String) {
        sendEvent(HomeEvent.CollectionClicked(collectionId, collectionName))
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


fun Movie.toMediaItemUiState(
    genreMap: Map<Int, String> = emptyMap(),
    formatDuration: (Movie) -> String = { "" }
): MediaItemUiState {
    return MediaItemUiState(
        id = id,
        title = name,
        posterPath = posterPath,
        rating = rating,
        genres = genreIds.mapNotNull { genreId -> genreMap[genreId] },
        releaseDate = releaseDate.toString(), // Convert LocalDate to String
        duration = formatDuration(this),
        mediaType = MediaType.Movie, // Explicitly set to Movie type
        backdropPath = backdropPath
    )
}

fun List<Movie>.toMediaItemUiState(
    genreMap: Map<Int, String> = emptyMap(),
    formatDuration: (Movie) -> String = { "" }
): List<MediaItemUiState> {
    return map { it.toMediaItemUiState(genreMap, formatDuration) }
}