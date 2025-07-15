package com.moscow.cineverse.screen.cast_details_gallery

import androidx.lifecycle.SavedStateHandle
import com.android.domain.usecase.actordetails.GetActorGallery
import com.moscow.cineverse.base.BaseViewModel

class ActorGalleryViewModel(
    private val getActorGallery: GetActorGallery,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ShowAllActorMoviesState, ActorGalleryEvents>(ShowAllActorMoviesState()),
    ActorGalleryInteractionListener {
    //todo enhance when make navigation
    private val actorId = savedStateHandle.get<Int>("actorId") ?: 1

    init {
        getActorPhotos()
    }


    private fun getActorPhotos() {
        launchWithFlow(
            flowAction = {
                getActorGallery.getActorGallery(actorId)
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