package com.moscow.cineverse.screen.castDetails.gallery

import com.android.domain.usecase.actordetails.GetActorGallery
import com.moscow.cineverse.base.BaseViewModel

class ActorGalleryViewModel(
    private val getActorGallery: GetActorGallery,
    actorId: Int,
    actorName: String,
) : BaseViewModel<ShowAllActorMoviesState, ActorGalleryEffect>(ShowAllActorMoviesState()),
    ActorGalleryInteractionListener {

    init {
        getActor(actorId, actorName)
        getActorPhotos()
    }

    fun getActor(actorId: Int, actorName: String){
        updateState { it.copy(actorId = actorId, actorName = actorName) }
    }

    fun getActorPhotos() {
        launchWithFlow(
            flowAction = {
                getActorGallery.getActorGallery(uiState.value.actorId)
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