package com.moscow.cineverse.screen.history

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.home.toMediaItemUiState
import com.moscow.domain.model.MediaType
import com.moscow.domain.usecase.recently_viewed.DeleteRecentlyViewedItemByIdUseCase
import com.moscow.domain.usecase.collection.CloseCollectionDetailsTipUseCase
import com.moscow.domain.usecase.collection.GetShowCollectionDetailsTipUseCase
import com.moscow.domain.usecase.recently_viewed.GetRecentlyViewedMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRecentlyViewedMediaUseCase: GetRecentlyViewedMediaUseCase,
    private val closeCollectionDetailsTipUseCase: CloseCollectionDetailsTipUseCase,
    private val getShowCollectionDetailsTipUseCase: GetShowCollectionDetailsTipUseCase,
    private val deleteRecentlyViewedItemByIdUseCase: DeleteRecentlyViewedItemByIdUseCase
) : BaseViewModel<HistoryScreenState, HistoryEffect>(HistoryScreenState()),
    HistoryInteractionListener {

    init {
        getShowTip()
        getRecentlyViewedMovies()
    }

    private fun getRecentlyViewedMovies() {
        launchWithResult(
            action = { getRecentlyViewedMediaUseCase() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        youRecentlyViewed = result.toMediaItemUiState(),
                        isContentEmpty = result.isEmpty()
                    )
                }
            },
            onError = {}
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
                        errorMessage = e
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } }
        )
    }

    override fun onBackPressed() {
        sendEvent(HistoryEffect.NavigateBack)
    }

    override fun onMediaItemClicked(mediaItemUiState: MediaItemUiState) {
        if (mediaItemUiState.mediaType == MediaType.Movie)
            sendEvent(HistoryEffect.MovieClicked(mediaItemUiState.id))
        else
            sendEvent(HistoryEffect.SeriesClicked(mediaItemUiState.id))
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
                        errorMessage = e
                    )
                }
            }
        )
    }

    override fun onItemDeletedIconClicked(mediaId: Int) {
        updateState { it.copy(isError = false, errorMessage = null) }
        launchAndForget(
            action = {
                deleteRecentlyViewedItemByIdUseCase(id = mediaId)
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMessage = e
                    )
                }
            }
        )
    }

    override fun onFindToSomethingToWatchButton() {
        sendEvent(HistoryEffect.WatchSomethingButtonClicked)
    }
}