package com.moscow.cineverse.screen.my_collections.create_collection_dialog

interface CreateCollectionDialogInteractionListener {
    fun onCancelClick()
    fun onCreateClick()
    fun onCollectionNameChange(name: String)
}