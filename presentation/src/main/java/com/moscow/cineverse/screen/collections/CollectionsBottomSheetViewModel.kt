package com.moscow.cineverse.screen.collections

import androidx.lifecycle.SavedStateHandle
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.mapper.toCollectionUi
import com.moscow.cineverse.navigation.routes.CollectionsBottomSheetRoute
import com.moscow.cineverse.screen.collections.CollectionsBottomSheetEffect.OnLoginClicked
import com.moscow.domain.model.Collection
import com.moscow.domain.usecase.collection.AddMediaItemToCollectionUseCase
import com.moscow.domain.usecase.collection.GetCurrentUserUseCase
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionsBottomSheetViewModel @Inject constructor(
    private val getUserCollections: GetUserCollectionsUseCase,
    private val addMediaItemToCollectionUseCase: AddMediaItemToCollectionUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionsBottomSheetScreenState, CollectionsBottomSheetEffect>(
    CollectionsBottomSheetScreenState()
), CollectionsBottomSheetInteractionListener {

    val mediaItemId: Int = savedStateHandle.get<Int>(CollectionsBottomSheetRoute.MEDIA_ITEM_ID) ?: 0

    init {
        isUserLoggedIn()
    }

    fun isUserLoggedIn() {
        launchWithResult(
            action = {
                getCurrentUserUseCase()
            },
            onSuccess = ::onUserLoggedInSuccess,
            onError = {},
            onStart = {},
            onFinally = {}
        )
    }

    private fun onUserLoggedInSuccess(isLoggedIn: Boolean) {
        isLoading(false)
        updateState { it.copy(isLoggedIn = isLoggedIn) }
        if (isLoggedIn) {
            loadUserCollections()
        }
    }

    private fun isLoading(loading: Boolean) {
        updateState { it.copy(isLoading = loading) }
    }

    override fun onAddNewCollectionClick() {
        sendEvent(CollectionsBottomSheetEffect.OnCreateCollectionClicked)
    }

    override fun onCollectionClicked(collectionId: Int) {
        launchAndForget(
            action = {
                addMediaItemToCollectionUseCase(
                    mediaItemId = mediaItemId,
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

    private fun onAddMediaItemToCollectionSuccess() {
        sendEvent(CollectionsBottomSheetEffect.OnItemAddedSuccessfully)
    }

    private fun onAddMediaItemToCollectionFailed(e: Int) {
        sendEvent(CollectionsBottomSheetEffect.OnItemAddedFailed(message = e))
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
        sendEvent(CollectionsBottomSheetEffect.OnCreateCollectionClicked)
    }

    override fun navigateToLogin() {
        sendEvent(OnLoginClicked)
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
        updateState {
            it.copy(
                collections = collections.map { collection -> collection.toCollectionUi() })
        }
    }

    private fun onLoadUserCollectionsFailed(msg: Int) {
        updateState { it.copy(errorMessage = msg) }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }
}