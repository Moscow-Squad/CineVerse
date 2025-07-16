package com.moscow.cineverse.screen

import com.android.domain.model.Collection
import com.android.domain.usecase.GetUserCollectionsUseCase
import com.moscow.cineverse.base.BaseViewModel

class CollectionsBottomSheetViewModel(
    private val getUserCollections: GetUserCollectionsUseCase
) : BaseViewModel<CollectionsBottomSheetUiState, CollectionsBottomSheetEvents>(
    CollectionsBottomSheetUiState()
), CollectionsBottomSheetInteractionListener {

    init {
        loadUserCollections()
    }

    override fun onShowCollectionsBottomSheet() {
        updateState { it.copy(showBottomSheet = true) }
    }

    override fun onDismissBottomSheet() {
        updateState { it.copy(showBottomSheet = false) }
    }

    override fun onAddNewCollectionClick() {
        TODO("should open new bottom sheet to login or to create new collection")
    }

    override fun onCollectionClick() {
        // TODO("show progress indicator that points to loading or request to add movie to this collection")
        updateState { it.copy(showProcessIndicator = true) }
    }

    override fun onRefresh() {
        loadUserCollections()
    }

    override fun onCloseBottomSheet() {
        updateState { it.copy(showBottomSheet = false) }
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
        updateState { it.copy(collections = collections) }
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