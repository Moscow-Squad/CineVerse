package com.moscow.cineverse.screen.home

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.base.handleException
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.mapper.toMyCollectionUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.repository.language.LanguageProvider
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.home.ClearHomeCash
import com.moscow.domain.usecase.home.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.home.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.home.GetTopRatedTVShowsUseCase
import com.moscow.domain.usecase.home.GetTrendingMoviesUseCase
import com.moscow.domain.usecase.home.GetUpcomingMoviesUseCase
import com.moscow.domain.usecase.local.GetUserDetailsUseCase
import com.moscow.domain.usecase.recently_viewed.GetRecentlyViewedMediaUseCase
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
    private val clearHomeCash: ClearHomeCash,
    private val blurProvider: BlurProvider,
    private val languageProvider: LanguageProvider
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()), HomeInteractionListener {

    init {
        viewModelScope.launch {
            val lang = languageProvider.languageFlow.first()
            updateState { it.copy(cashLanguage = lang) }
        }
        observeLanguage()
        getGenres()
        observeBlur()
    }

    private fun getGenres() {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(movieGenre = genres.map { genre -> genre.toUi() })
                }
                loadHomeData()
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
            languageProvider.languageFlow.collect { currentLanguage ->
                if (uiState.value.cashLanguage != currentLanguage) {
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
        viewModelScope.launch {
            try {
                val jobs = listOf(
                    async { getUserDetails() },
                    async { fetchTrendingMovies() },
                    async { fetchRecentlyReleasedMovies() },
                    async { fetchUpcomingMovies() },
                    async { fetchTopRatedTVShows() },
                    async { fetchMatchesYourVibesMovies() }
                )
                jobs.awaitAll()
                updateState { it.copy(isLoading = false) }
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
                updateState { it.copy(collections = collections.map { collection -> collection.toMyCollectionUi() }) }
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
                        youRecentlyViewed = result.toMediaItemUiState()
                    )
                }
            },
            onError = {}
        )
    }

    private suspend fun fetchTrendingMovies() {
        val res = getTrendingMoviesUseCase()
        onFetchTrendingMoviesSuccess(res)
    }

    private fun onFetchTrendingMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                sliderItems = movies.map { it.toUi(uiState.value.movieGenre) },
            )
        }
    }

    private suspend fun fetchRecentlyReleasedMovies() {
        val res = getRecentlyReleasedMoviesUseCase(page = 1)
        onFetchRecentlyReleasedMoviesSuccess(res)
    }

    private fun onFetchRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                recentlyReleasedMovies = movies.map { it.toUi(uiState.value.movieGenre) },
            )
        }
    }

    private suspend fun fetchUpcomingMovies() {
        val res = getUpcomingMoviesUseCase(page = 1)
        onFetchUpcomingMoviesSuccess(res)
    }

    private fun onFetchUpcomingMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                upcomingMovies = movies.map { it.toUi(uiState.value.movieGenre) },
            )
        }
    }

    private suspend fun fetchTopRatedTVShows() {
        val res = getTopRatedTVShowsUseCase(page = 1)
        onFetchTopRatedTVShowsSuccess(res)
    }

    private fun onFetchTopRatedTVShowsSuccess(tvShows: List<Series>) {
        updateState {
            it.copy(
                topRatedTvShows = tvShows.toUi(),
            )
        }
    }

    private suspend fun fetchMatchesYourVibesMovies() {
        val res = getMatchesYourVibesMoviesUseCase(genreId = 28, page = 1)
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
            sendEvent(HomeEvent.MovieClicked(mediaItemUiState.id))
        else
            sendEvent(HomeEvent.SeriesClicked(mediaItemUiState.id))
    }

    override fun onSeeAllClick(type: HomeFeaturedItems) {
        sendEvent(HomeEvent.SeeAllClicked(type))
    }

    override fun onCollectionsShowMoreClick() {
        sendEvent(HomeEvent.SeeMoreCollections)
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
        getGenres()
    }

    override fun onSeeMoreRecentlyViewedClicked() {
        sendEvent(HomeEvent.SeeMoreRecentlyViewed)
    }

    override fun onFeaturedCollectionClick(genreId: Int) {
        val collection = HomeFeaturedCollections.entries.find { it.genreId == genreId }
        if (collection != null) {
            sendEvent(
                HomeEvent.FeaturedCollectionClicked(
                    collection.genreId,
                    collection.title
                )
            )
        }
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