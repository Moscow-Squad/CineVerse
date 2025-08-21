package com.moscow.cineverse.screen.home

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.base.handleException
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState.MediaType
import com.moscow.cineverse.mapper.toCollectionUi
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.model.UserType
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.service.language.LanguageProvider
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.movie.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.movie.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.movie.GetTrendingMoviesUseCase
import com.moscow.domain.usecase.movie.GetUpcomingMoviesUseCase
import com.moscow.domain.usecase.preference.GetUserDetailsUseCase
import com.moscow.domain.usecase.recently_viewed.GetRecentlyViewedMediaUseCase
import com.moscow.domain.usecase.series.GetTopRatedTVShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    private val getUserCollectionsUseCase: GetUserCollectionsUseCase,
    private val getRecentlyViewedMediaUseCase: GetRecentlyViewedMediaUseCase,
    private val genreUseCase: GenreUseCase,
    private val blurProvider: BlurProvider,
    private val languageProvider: LanguageProvider,
) : BaseViewModel<HomeScreenState, HomeEffect>(HomeScreenState()), HomeInteractionListener {

    init {
        viewModelScope.launch {
            val lang = languageProvider.currentLanguage.first()
            updateState { it.copy(cashLanguage = lang) }
        }
        observeLanguage()
        getGenres()
        observeBlur()
    }

    private fun getGenres(refresh: Boolean = false) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(movieGenre = genres.map { genre -> genre.toUi() })
                }
                loadHomeData(refresh)
            },
            onError = { e ->
                updateState {
                    it.copy(
                        error = e,
                        isLoading = false,
                    )
                }
            },
        )
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            languageProvider.currentLanguage.collect { currentLanguage ->
                if (uiState.value.cashLanguage != currentLanguage) {
                    updateState { it.copy(cashLanguage = currentLanguage) }
                    getGenres(refresh = true)
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

    private fun loadHomeData(refresh: Boolean = false) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                fetchTrendingMovies(refresh)
                fetchRecentlyReleasedMovies(refresh)
                updateState { it.copy(isLoading = false) }

                val secondaryJobs = listOf(
                    async { getUserDetails() },
                    async { fetchUpcomingMovies(refresh) },
                    async { fetchTopRatedTVShows(refresh) },
                    async { fetchMatchesYourVibesMovies(refresh) }
                )
                secondaryJobs.awaitAll()
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = e.handleException()
                    )
                }
            }
        }
    }

    private fun getUserCollection() {
        launchWithResult(
            action = { getUserCollectionsUseCase(1) },
            onSuccess = { collections ->
                updateState { it.copy(collections = collections.map { collection -> collection.toCollectionUi() }) }
            },
            onError = { e ->
                updateState { it.copy(isLoading = false, error = e) }
            },
        )
    }

    private suspend fun getUserDetails() {
        val res = getUserDetailsUseCase()
        onGetUserDetailsSuccess(res)
    }

    private fun onGetUserDetailsSuccess(user: UserType) {
        when (user) {
            is UserType.AuthenticatedUser -> {
                updateState { it.copy(userName = user.name.ifEmpty { user.username }) }
                getUserCollection()
            }

            is UserType.GuestUser -> {
                updateState { it.copy(userName = null) }
            }
        }
        getRecentlyViewedMovies()
    }

    fun getRecentlyViewedMovies() {
        launchWithFlow(
            flowAction = { getRecentlyViewedMediaUseCase() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                       youRecentlyViewed = result.map { mediaItem ->
                           when(mediaItem){
                               is Movie -> mediaItem.toUi(uiState.value.movieGenre)
                               is Series -> mediaItem.toMediaItemUi()
                               else -> MediaItemUiState()
                           }
                        },
                    )
                }
            },
            onError = {}
        )
    }

    private suspend fun fetchTrendingMovies(refresh: Boolean = false) {
        val res = getTrendingMoviesUseCase(forceRefresh = refresh)
        onFetchTrendingMoviesSuccess(res)
    }

    private fun onFetchTrendingMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                sliderItems = movies.map { it.toUi(uiState.value.movieGenre) },
            )
        }
    }

    private suspend fun fetchRecentlyReleasedMovies(refresh: Boolean = false) {
        val res = getRecentlyReleasedMoviesUseCase(forceRefresh = refresh, page = 1)
        onFetchRecentlyReleasedMoviesSuccess(res)
    }

    private fun onFetchRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                recentlyReleasedMovies = movies.map { it.toUi(uiState.value.movieGenre) },
            )
        }
    }

    private suspend fun fetchUpcomingMovies(refresh: Boolean = false) {
        val res = getUpcomingMoviesUseCase(forceRefresh = refresh, page = 1)
        onFetchUpcomingMoviesSuccess(res)
    }

    private fun onFetchUpcomingMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                upcomingMovies = movies.map { it.toUi(uiState.value.movieGenre) },
            )
        }
    }

    private suspend fun fetchTopRatedTVShows(refresh: Boolean = false) {
        val res = getTopRatedTVShowsUseCase(forceRefresh = refresh, page = 1)
        onFetchTopRatedTVShowsSuccess(res)
    }

    private fun onFetchTopRatedTVShowsSuccess(tvShows: List<Series>) {
        updateState {
            it.copy(
                topRatedTvShows = tvShows.toUi(),
            )
        }
    }

    private suspend fun fetchMatchesYourVibesMovies(refresh: Boolean = false) {
        val res = getMatchesYourVibesMoviesUseCase(
            forceRefresh = refresh,
            genreId = 28,
            page = 1
        )
        onFetchMatchesYourVibesMoviesSuccess(res)
    }

    private fun onFetchMatchesYourVibesMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                matchesYourVibe = movies.map { it.toUi(uiState.value.movieGenre) },
                error = null,
            )
        }
    }

    override fun onMediaItemClicked(mediaItemUiState: MediaItemUiState) {
       if (mediaItemUiState.mediaType == MediaType.Movie)
            sendEvent(HomeEffect.MovieClicked(mediaItemUiState.id))
       else {
           sendEvent(HomeEffect.SeriesClicked(mediaItemUiState.id))
       }
    }

    override fun onSeeAllClick(type: HomeFeaturedItems) {
        sendEvent(HomeEffect.SeeAllClicked(type))
    }

    override fun onCollectionsShowMoreClick() {
        sendEvent(HomeEffect.SeeMoreCollections)
    }

    override fun onCollectionClick(collectionId: Int, collectionName: String) {
        sendEvent(HomeEffect.CollectionClicked(collectionId, collectionName))
    }

    override fun onWatchSuggestionClick() {
        sendEvent(HomeEffect.WatchingSuggestionClicked)
    }

    override fun onBrowseSuggestionClick() {
        sendEvent(HomeEffect.BrowseSuggestionClicked)
    }

    override fun onRefresh() {
        getGenres()
    }

    override fun onSeeMoreRecentlyViewedClicked() {
        sendEvent(HomeEffect.SeeMoreRecentlyViewed)
    }

    override fun onFeaturedCollectionClick(genreId: Int) {
        val collection = HomeFeaturedCollections.entries.find { it.genreId == genreId }
        if (collection != null) {
            sendEvent(
                HomeEffect.FeaturedCollectionClicked(
                    collection.genreId,
                    collection.title
                )
            )
        }
    }
}

