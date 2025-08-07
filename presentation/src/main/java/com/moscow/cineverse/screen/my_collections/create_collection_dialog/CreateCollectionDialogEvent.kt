package com.moscow.cineverse.screen.my_collections.create_collection_dialog

sealed interface CreateCollectionDialogEvent {
    data object OnCancelCollectionCreation: CreateCollectionDialogEvent
    data object OnCollectionAddedSuccessfully : CreateCollectionDialogEvent
    data class OnAddCollectionFailed(val message: String) : CreateCollectionDialogEvent
}