package com.moscow.cineverse.screen.my_collections

import com.moscow.cineverse.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCollectionsViewModel @Inject constructor(

):
    BaseViewModel<MyCollectionsUiState, MyCollectionsEvent>(
        MyCollectionsUiState()
    ), MyCollectionsInteractionListener {
    override fun onBackClick() {
        sendEvent(MyCollectionsEvent.OnNavigateBack)
    }

    override fun onCreateCollectionClick() {
        sendEvent(MyCollectionsEvent.OnNavigateToCreateCollection)
    }

    override fun onCollectionClick(collectionId: Int) {
        sendEvent(MyCollectionsEvent.OnNavigateToCollection(collectionId))
    }

}