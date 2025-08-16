package com.moscow.cineverse.screen.my_collections

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.CollectionUiState
import com.moscow.cineverse.mapper.toCollectionUi
import com.moscow.domain.model.Collection
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCollectionsViewModel @Inject constructor(
    private val getUserCollections: GetUserCollectionsUseCase,
) :
    BaseViewModel<MyCollectionsUiState, MyCollectionsEvent>(
        MyCollectionsUiState()
    ), MyCollectionsInteractionListener {

    init {
        loadUserCollections()
    }

    override fun onBackClick() {
        sendEvent(MyCollectionsEvent.OnNavigateBack)
    }

    override fun onCreateCollectionClick() {
        sendEvent(MyCollectionsEvent.OnNavigateToCreateCollection)
    }

    override fun onCollectionClick(collectionId: Int, collectionName: String) {
        sendEvent(MyCollectionsEvent.OnNavigateToCollection(collectionId, collectionName))
    }

    override fun onStartCollectingClick() {
        sendEvent(MyCollectionsEvent.OnStartCollecting)
    }

    private fun loadUserCollections() {
        updateState { it.copy(errorMessage = null) }
        launchWithResult(
            action = { getUserCollections(page = 1) },
            onSuccess = ::onLoadUserCollectionsSuccess,
            onError = ::onLoadUserCollectionsFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    override fun insertNewCollection(collectionId: Int, collectionName: String) {
        updateState {
            it.copy(
                collections = listOf(
                    CollectionUiState(
                        id = collectionId,
                        title = collectionName,
                        numberOfItems = 0,
                        isLoading = false
                    )
                ) + it.collections
            )
        }
    }

    override fun onRetry() {
        updateState { it.copy(errorMessage = null) }
        loadUserCollections()
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