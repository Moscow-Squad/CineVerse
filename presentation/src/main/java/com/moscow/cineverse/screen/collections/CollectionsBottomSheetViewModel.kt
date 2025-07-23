package com.moscow.cineverse.screen.collections

import androidx.lifecycle.SavedStateHandle
import com.android.domain.model.Collection
import com.android.domain.model.MediaType
import com.android.domain.usecase.collection.AddMediaItemToCollectionUseCase
import com.android.domain.usecase.collection.GetUserCollectionsUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute

class CollectionsBottomSheetViewModel(
    private val getUserCollections: GetUserCollectionsUseCase,
    private val addMediaItemToCollectionUseCase: AddMediaItemToCollectionUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionsBottomSheetScreenState, CollectionsBottomSheetEffect>(
    CollectionsBottomSheetScreenState()
), CollectionsBottomSheetInteractionListener {

    val mediaItemId: Int = savedStateHandle.get<Int>(CollectionsBottomSheetRoute.MEDIA_ITEM_ID) ?: 0
    val mediaItemType: MediaType = MediaType.toMediaType(
        savedStateHandle.get<String>(CollectionsBottomSheetRoute.MEDIA_TYPE) ?: "movie"
    )

    override fun onAddNewCollectionClick() {
        TODO("should open new bottom sheet to login or to create new collection")
    }

    override fun onCollectionClicked(collectionId: Int) {
        launchWithResult(
            action = {
                addMediaItemToCollectionUseCase.invoke(
                    mediaItemId = mediaItemId,
                    mediaItemType = mediaItemType,
                    collectionId = collectionId
                )
            },
            onSuccess = ::onAddMediaItemToCollectionSuccess,
            onError = ::onAddMediaItemToCollectionFailed,
            onStart = {
                isAddMediaItemToCollectionLoading(
                    collectionId = collectionId,
                    isLoading = true
                )
            },
            onFinally = {
                isAddMediaItemToCollectionLoading(
                    collectionId = collectionId,
                    isLoading = false
                )
            }
        )
    }

    private fun onAddMediaItemToCollectionSuccess(message: String) {
        sendEvent(CollectionsBottomSheetEffect.OnMovieAddedSuccessfully("Movie added successfully"))
    }

    private fun onAddMediaItemToCollectionFailed(e: Throwable) {

    }

    private fun isAddMediaItemToCollectionLoading(collectionId: Int, isLoading: Boolean) {
        updateState {
            it.copy(collections = it.collections.map { collection ->
                if (collection.id == collectionId) {
                    collection.copy(isLoading = isLoading)
                } else {
                    collection
                }
            })
        }
    }


    override fun onCreateCollectionClicked() {
        updateState { it.copy(createCollection = true) }

    }

    override fun onRefresh() {
        loadUserCollections()
    }

    override fun onShowCollectionsBottomSheet(show: Boolean) {
        updateState { it.copy(showBottomSheet = show) }
    }

    private fun loadUserCollections() {
        launchWithResult(
            action = { getUserCollections(page = 1) },
            onSuccess = ::onLoadUserCollectionsSuccess,
            onError = ::onLoadUserCollectionsFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onLoadUserCollectionsSuccess(collections: List<Collection>) {
        updateState { it.copy(collections = collections.take(5).map { collection -> collection.toUi() }) }
    }

    private fun onLoadUserCollectionsFailed(throwable: Throwable) {
        updateState { it.copy(errorMessage = "Error in loading collections: ${throwable.message}") }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }
}