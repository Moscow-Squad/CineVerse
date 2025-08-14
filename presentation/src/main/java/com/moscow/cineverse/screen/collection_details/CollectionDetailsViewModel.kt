package com.moscow.cineverse.screen.collection_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.navigation.routes.CollectionDetailsRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.MediaType
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.usecase.collection.ClearCollectionUseCase
import com.moscow.domain.usecase.collection.CloseCollectionDetailsTipUseCase
import com.moscow.domain.usecase.collection.DeleteMediaItemFromCollectionUseCase
import com.moscow.domain.usecase.collection.GetCollectionDetailsUseCase
import com.moscow.domain.usecase.collection.GetShowCollectionDetailsTipUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val deleteMediaFromCollectionUseCase: DeleteMediaItemFromCollectionUseCase,
    private val getCollectionMediaItemsUseCase: GetCollectionDetailsUseCase,
    private val clearCollectionUseCase: ClearCollectionUseCase,
    private val genreUseCase: GenreUseCase,
    private val getShowCollectionDetailsTipUseCase: GetShowCollectionDetailsTipUseCase,
    private val closeCollectionDetailsTipUseCase: CloseCollectionDetailsTipUseCase,
    private val blurProvider: BlurProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionDetailsScreenState, CollectionDetailsEffect>(
    CollectionDetailsScreenState()
),
    CollectionDetailsInteractionListener {

    val collectionId = savedStateHandle.get<Int>(CollectionDetailsRoute.COLLECTION_ID) ?: 0
    val collectionName = savedStateHandle.get<String>(CollectionDetailsRoute.COLLECTION_NAME) ?: ""

    private var currentPagingSource: BasePagingSource<MediaItemUiState>?  = null

    init {
        getMoviesGenres()
        getShowTip()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun getMoviesGenres() {
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(
                        moviesGenres = genres.map { genre -> genre.toUi() },
                        isLoading = false
                    )
                }
                getMovies()
            },
            onError = { msg ->
                updateState {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        errorMsg = msg
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }

    private fun getShowTip() {
        launchWithResult(
            action = getShowCollectionDetailsTipUseCase::invoke,
            onSuccess = { res ->
                updateState {
                    it.copy(
                        showTip = res,
                        isLoading = false
                    )
                }
            },
            onError = { msg ->
                updateState {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        errorMsg = msg
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } }
        )
    }

    private fun getMovies() {
        launchWithResult(
            action = {
                return@launchWithResult Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        currentPagingSource = BasePagingSource { page ->
                            getCollectionMediaItemsUseCase(
                                collectionId = collectionId,
                                page = page
                            ).map {
                                it.toUi(uiState.value.moviesGenres)
                            }
                        }
                        currentPagingSource!!
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { res ->
                updateState { it.copy(movies = res) }
            },
            onError = { msg ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMsg = msg
                    )
                }
            },
        )
    }

    override fun onBackButtonClicked() {
        sendEvent(CollectionDetailsEffect.NavigateBack)
    }

    override fun onMediaItemClicked(
        mediaId: Int,
        mediaType: MediaType
    ) {
        if (mediaType == MediaType.Movie) {
            sendEvent(CollectionDetailsEffect.NavigateToMovieDetails(mediaId))
        } else if (mediaType == MediaType.Tv) {
            sendEvent(CollectionDetailsEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onItemDeletedIconClicked(
        mediaId: Int,
        mediaType: MediaType
    ) {
        updateState { it.copy(isError = false, errorMsg = 0) }
        launchAndForget(
            action = {
                deleteMediaFromCollectionUseCase(
                    collectionId = collectionId,
                    mediaItemId = mediaId
                )
            },
            onSuccess = {
                currentPagingSource?.invalidate()
            },
            onError = { msg ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMsg = msg
                    )
                }
            }
        )
    }

    override fun clearCollection() {
        updateState { it.copy(isLoading = true, isError = false, errorMsg = 0) }
        launchAndForget(
            action = {
                clearCollectionUseCase(
                    collectionId = collectionId,
                    confirm = uiState.value.confirmClear
                )
            },
            onSuccess = { updateState { it.copy(isLoading = false) } },
            onError = { msg ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMsg = msg
                    )
                }
            }
        )
    }

    override fun onTipCancelIconClicked() {
        updateState { it.copy(isLoading = false) }
        launchAndForget(
            action = { closeCollectionDetailsTipUseCase() },
            onSuccess = { updateState { it.copy(showTip = false) } },
            onError = { msg ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMsg = msg
                    )
                }
            }
        )
    }

    override fun onRefresh() {
        updateState {
            it.copy(
                isLoading = true,
                isError = false,
                errorMsg = 0,
            )
        }
        getMoviesGenres()
        getShowTip()
        observeBlur()
    }

    override fun onStartCollectingClick() {
        sendEvent(CollectionDetailsEffect.OnStartCollecting)
    }
}