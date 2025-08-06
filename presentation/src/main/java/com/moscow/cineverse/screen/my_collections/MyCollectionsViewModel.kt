package com.moscow.cineverse.screen.my_collections

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MyCollectionUiState
import com.moscow.cineverse.mapper.toMyCollectionUi
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
                    MyCollectionUiState(
                        id = collectionId,
                        title = collectionName,
                        itemCount = 0
                    )
                ) + it.collections
            )
        }
    }


    private fun onLoadUserCollectionsSuccess(collections: List<Collection>) {
        updateState {
            it.copy(
                collections = collections.map { collection -> collection.toMyCollectionUi() })
        }
    }

    private fun onLoadUserCollectionsFailed(throwable: Throwable) {
        updateState { it.copy(errorMessage = throwable.message.toString()) }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }
}