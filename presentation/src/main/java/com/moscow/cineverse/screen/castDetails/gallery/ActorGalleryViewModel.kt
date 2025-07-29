package com.moscow.cineverse.screen.castDetails.gallery

import androidx.lifecycle.SavedStateHandle
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CastGalleryRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.domain.usecase.actor.GetActorGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorGalleryViewModel @Inject constructor(
    private val getActorGalleryUseCase: GetActorGalleryUseCase,
                                                savedStateHandle: SavedStateHandle
) : BaseViewModel<ShowAllActorMoviesState, ActorGalleryEffect>(ShowAllActorMoviesState()),
    ActorGalleryInteractionListener {
    private val castID = savedStateHandle.get<Int>(CastGalleryRoute.CAST_ID) ?: 0
    private val castName = savedStateHandle.get<String>(CastGalleryRoute.CAST_NAME) ?: ""

    init {
        getActor(castID, castName)
        getActorPhotos()
    }

    fun getActor(actorId: Int, actorName: String){
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

    private fun onGetActorPhotosFailed(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
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
        //todo enhance when make navigation
    }
}