package com.moscow.cineverse.screen.collection_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CollectionDetailsRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.usecase.collection.ClearCollectionUseCase
import com.moscow.domain.usecase.collection.CloseCollectionDetailsTipUseCase
import com.moscow.domain.usecase.collection.DeleteMediaItemFromCollectionUseCase
import com.moscow.domain.usecase.collection.GetCollectionDetailsUseCase
import com.moscow.domain.usecase.collection.GetShowCollectionDetailsTipUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val deleteMediaFromCollectionV4UseCase: DeleteMediaItemFromCollectionUseCase,
    private val getCollectionMediaItemsV4UseCase: GetCollectionDetailsUseCase,
    private val clearCollectionUseCase: ClearCollectionUseCase,
    private val genreUseCase: GenreUseCase,
    private val getShowCollectionDetailsTipUseCase: GetShowCollectionDetailsTipUseCase,
    private val closeCollectionDetailsTipUseCase: CloseCollectionDetailsTipUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionDetailsScreenState, CollectionDetailsEffect>(
    CollectionDetailsScreenState()
),
    CollectionDetailsInteractionListener {

    val collectionId = savedStateHandle.get<Int>(CollectionDetailsRoute.COLLECTION_ID) ?: 0
    val collectionName = savedStateHandle.get<String>(CollectionDetailsRoute.COLLECTION_NAME) ?: ""

    init {
        getMoviesGenres()
        getShowTip()
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
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        errorMsg = e.message.toString()
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
            onError = { e ->
                updateState {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        errorMsg = e.message.toString()
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } }
        )
    }

    fun getMediaItems(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getCollectionMediaItemsV4UseCase(collectionId, page)
                }
            }
        ).flow.cachedIn(viewModelScope)
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
        updateState { it.copy(isError = false, errorMsg = "") }
        launchAndForget(
            action = {
                deleteMediaFromCollectionV4UseCase(
                    collectionId = collectionId,
                    mediaItemId = mediaId
                )
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMsg = e.message.toString()
                    )
                }
            }
        )
    }

    override fun clearCollection() {
        updateState { it.copy(isLoading = true, isError = false, errorMsg = "") }
        launchAndForget(
            action = {
                clearCollectionUseCase(
                    collectionId = collectionId,
                    confirm = uiState.value.confirmClear
                )
            },
            onSuccess = { updateState { it.copy(isLoading = false) } },
            onError = { e ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMsg = e.message.toString()
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
            onError = { e ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMsg = e.message.toString()
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
                errorMsg = "",
            )
        }
        getMoviesGenres()
    }
}