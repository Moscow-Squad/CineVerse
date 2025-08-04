package com.moscow.cineverse.screen.my_collections.create_collection_dialog

import com.moscow.cineverse.base.BaseViewModel

class CreateCollectionDialogViewModel :
    BaseViewModel<CreateCollectionDialogUiState, CreateCollectionDialogEvent>(
        CreateCollectionDialogUiState()
    ), CreateCollectionDialogInteractionListener {
    override fun onCancelClick() {
        sendEvent(CreateCollectionDialogEvent.OnCancelCollectionCreation)
    }

    override fun onCreateClick() {

    }

    override fun onCollectionNameChange(name: String) {
        updateState {
            it.copy(
                collectionName = name
            )
        }
    }
}