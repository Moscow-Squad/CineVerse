package com.moscow.cineverse.screen.collections

import androidx.lifecycle.SavedStateHandle
import com.android.domain.model.Collection
import com.android.domain.model.MediaType
import com.android.domain.usecase.AddMediaItemToCollectionUseCase
import com.android.domain.usecase.GetUserCollectionsUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute
import com.moscow.cineverse.screen.model.toUi

class CollectionsBottomSheetViewModel(
    private val getUserCollections: GetUserCollectionsUseCase,
    private val addMediaItemToCollectionUseCase: AddMediaItemToCollectionUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionsBottomSheetScreenState, CollectionsBottomSheetEvents>(
    CollectionsBottomSheetScreenState()
), CollectionsBottomSheetInteractionListener {

    val mediaItemId: Int = savedStateHandle.get<Int>(CollectionsBottomSheetRoute.MEDIA_ITEM_ID) ?: 0
    val mediaItemType: MediaType = MediaType.toMediaType(
        savedStateHandle.get<String>(CollectionsBottomSheetRoute.MEDIA_TYPE) ?: "movie"
    )

    init {
//        loadUserCollections()
    }

    override fun onAddNewCollectionClick() {
        TODO("should open new bottom sheet to login or to create new collection")
    }

    override fun onCollectionClicked(collectionId: Int) {
        launchWithFlow(
            flowAction = {
                addMediaItemToCollectionUseCase.addMediaItemToCollection(
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
        sendEvent(CollectionsBottomSheetEvents.OnMovieAddedSuccessfully("Movie added successfully"))
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
        launchWithFlow(
            flowAction = { getUserCollections.getUseCollections() },
            onSuccess = ::onLoadUserCollectionsSuccess,
            onError = ::onLoadUserCollectionsFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onLoadUserCollectionsSuccess(collections: List<Collection>) {
        updateState { it.copy(collections = collections.take(5).map { it.toUi() }) }
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