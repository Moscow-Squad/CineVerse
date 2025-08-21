package com.moscow.cineverse.screen.history

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState.MediaType
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.usecase.recently_viewed.CloseHistoryTipUseCase
import com.moscow.domain.usecase.recently_viewed.DeleteRecentlyViewedItemByIdUseCase
import com.moscow.domain.usecase.recently_viewed.GetRecentlyViewedMediaUseCase
import com.moscow.domain.usecase.recently_viewed.GetShowHistoryTipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRecentlyViewedMediaUseCase: GetRecentlyViewedMediaUseCase,
    private val closeHistoryTipUseCase: CloseHistoryTipUseCase,
    private val getShowHistoryTipUseCase: GetShowHistoryTipUseCase,
    private val deleteRecentlyViewedItemByIdUseCase: DeleteRecentlyViewedItemByIdUseCase,
    private val blurProvider: BlurProvider
) : BaseViewModel<HistoryScreenState, HistoryEffect>(HistoryScreenState()),
    HistoryInteractionListener {

    init {
        getShowTip()
        getRecentlyViewedMovies()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun getRecentlyViewedMovies() {
        launchWithFlow(
            flowAction = { getRecentlyViewedMediaUseCase() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        youRecentlyViewed = result.map{ item ->
                            when(item){
                                is Movie -> item.toMediaItemUi()
                                is Series -> item.toMediaItemUi()
                                else -> MediaItemUiState()
                            }
                        },
                        isContentEmpty = result.isEmpty(),
                        isLoading = false
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