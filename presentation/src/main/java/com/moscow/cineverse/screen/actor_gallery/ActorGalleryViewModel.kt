package com.moscow.cineverse.screen.actor_gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.usecase.actor.GetActorGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorGalleryViewModel @Inject constructor(
    private val getActorGalleryUseCase: GetActorGalleryUseCase,
    private val blurProvider: BlurProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ActorGalleryState, ActorGalleryEffect>(ActorGalleryState()),
    ActorGalleryInteractionListener {
    private val castID = savedStateHandle.get<Int>(CastGalleryRoute.CAST_ID) ?: 0
    private val castName = savedStateHandle.get<String>(CastGalleryRoute.CAST_NAME) ?: ""

    init {
        getActor(castID, castName)
        getActorPhotos()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    fun getActor(actorId: Int, actorName: String) {
        updateState { it.copy(actorId = actorId, actorName = actorName) }
    }

    fun getActorPhotos() {
        launchWithResult(
            action = {
                getActorGalleryUseCase.invoke(uiState.value.actorId)
            },
            onSuccess = ::onGetActorPhotosSuccess,
            onError = ::onGetActorPhotosFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetActorPhotosSuccess(photos: List<String>) {
        updateState {
            it.copy(
                photos = photos,
                error = null
            )
        }
    }

    private fun onGetActorPhotosFailed(msg: Int) {
        updateState { it.copy(error = msg) }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }

    override fun onRefresh() {
        getActorPhotos()
    }

    override fun backButtonClick() {
        sendEvent(ActorGalleryEffect.NavigateBack)
    }
}