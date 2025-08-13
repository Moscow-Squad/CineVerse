package com.moscow.cineverse.screen.history

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.home.toMediaItemUiState
import com.moscow.domain.model.MediaType
import com.moscow.domain.usecase.DeleteRecentlyViewedItemByIdUseCase
import com.moscow.domain.usecase.recently_viewed.CloseHistoryTipUseCase
import com.moscow.domain.usecase.recently_viewed.GetRecentlyViewedMediaUseCase
import com.moscow.domain.usecase.recently_viewed.GetShowHistoryTipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRecentlyViewedMediaUseCase: GetRecentlyViewedMediaUseCase,
    private val closeHistoryTipUseCase: CloseHistoryTipUseCase,
    private val getShowHistoryTipUseCase: GetShowHistoryTipUseCase,
    private val deleteRecentlyViewedItemByIdUseCase: DeleteRecentlyViewedItemByIdUseCase
) : BaseViewModel<HistoryScreenState, HistoryEffect>(HistoryScreenState()),
    HistoryInteractionListener {

    init {
        getShowTip()
        getRecentlyViewedMovies()
    }

    private fun getRecentlyViewedMovies() {
        launchWithFlow(
            flowAction = { getRecentlyViewedMediaUseCase() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        youRecentlyViewed = result.toMediaItemUiState(),
                        isContentEmpty = result.isEmpty()
                    )
                }
            },
            onStart = {
                updateState {
                    it.copy(
                        isError = false,
                        errorMessage = null,
                        isLoading = true
                    )
                }
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isError = true,
                        errorMessage = e,
                        isLoading = false
                    )
                }
            }
        )
    }

    private fun getShowTip() {
        launchWithResult(
            action = getShowHistoryTipUseCase::invoke,
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
            onStart = {
                updateState {
                    it.copy(
                        isError = false,
                        errorMessage = null,
                        isLoading = true
                    )
                }
            }
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
        launchAndForget(
            action = { closeHistoryTipUseCase() },
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

    override fun onRetry() {
        updateState { it.copy(isError = false, errorMessage = null) }
        getShowTip()
        getRecentlyViewedMovies()
    }
}