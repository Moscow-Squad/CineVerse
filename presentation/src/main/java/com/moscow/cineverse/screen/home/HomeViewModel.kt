package com.moscow.cineverse.screen.home


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.mapper.toMyCollectionUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.repository.language.LanguageProvider
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import com.moscow.domain.usecase.home.ClearHomeCash
import com.moscow.domain.usecase.home.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.home.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.home.GetTopRatedTVShowsUseCase
import com.moscow.domain.usecase.home.GetTrendingMoviesUseCase
import com.moscow.domain.usecase.home.GetUpcomingMoviesUseCase
import com.moscow.domain.usecase.local.GetUserDetailsUseCase
import com.moscow.domain.usecase.recently_viewed.GetRecentlyViewedMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMatchesYourVibesMoviesUseCase: GetMatchesYourVibesMoviesUseCase,
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val blurProvider: BlurProvider,
    private val getUserCollectionsUseCase: GetUserCollectionsUseCase,
    private val getRecentlyViewedMediaUseCase: GetRecentlyViewedMediaUseCase,
    private val clearHomeCash: ClearHomeCash,
    private val languageProvider: LanguageProvider
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()), HomeInteractionListener {

    init {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            val d = languageProvider.languageFlow.first()
            updateState { it.copy(cashLanguage = d) }
        }

        observeLanguage()
        loadHomeData()
        observeBlur()
    }

    private fun observeLanguage(){
        viewModelScope.launch {

            languageProvider.languageFlow.collect { currentLanguage ->
                if (uiState.value.cashLanguage != currentLanguage){
                    updateState { it.copy(cashLanguage = currentLanguage) }
                    clearHomeCash()
                    loadHomeData()
                }
            }
        }
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun loadHomeData() {
        updateState { it.copy(isLoading = true, error = null) }
        launchAndForget(
            action = {
                viewModelScope.launch {
                    coroutineScope {
                        val jobs = listOf(
                            launch { getUserDetails() },
                            launch { fetchTrendingMovies() },
                            launch { fetchRecentlyReleasedMovies() },
                            launch { fetchUpcomingMovies() },
                            launch { fetchTopRatedTVShows() },
                            launch { fetchMatchesYourVibesMovies() },
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
                updateState { it.copy(collections = collections.map { collection -> collection.toMyCollectionUi() }) }
            },
            onError = { e ->
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
                updateState { it.copy(userName = user.username) }
                getUserCollection()
            }

            is UserType.GuestUser -> {
                updateState { it.copy(userName = null) }
            }
        }
        getRecentlyViewedMovies()
    }

    private fun onGetUserDetailsError(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
    }

    fun getRecentlyViewedMovies() {
        launchWithResult(
            action = { getRecentlyViewedMediaUseCase() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        youRecentlyViewed = result.toMediaItemUiState()
                    )
                }
            },
            onError = {}
        )
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
        updateState { it.copy(sliderItems = movies.toUi()) }
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
                recentlyReleasedMovies = movies.toUi(),

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
                upcomingMovies = movies.toUi(),

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
                matchesYourVibe = movies.toUi(),
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

fun List<Any>.toMediaItemUiState(
    genreMap: Map<Int, String> = emptyMap(),
    formatDuration: (Movie) -> String = { "" }
): List<MediaItemUiState> {
    return mapNotNull { item ->
        when (item) {
            is Movie -> item.toMediaItemUiState(genreMap, formatDuration)
            is Series -> MediaItemUiState(
                id = item.id,
                title = item.name,
                posterPath = item.posterPath,
                rating = item.rating,
                genres = item.genreIds.mapNotNull { genreId -> genreMap[genreId] },
                releaseDate = item.firstAirDate.toString(),
                duration = "",
                mediaType = MediaType.Tv,
                backdropPath = item.backdropPath
            )

            else -> null
        }
    }
}