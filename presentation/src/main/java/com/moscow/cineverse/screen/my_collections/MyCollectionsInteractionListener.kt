package com.moscow.cineverse.screen.my_collections

interface MyCollectionsInteractionListener {
    fun onBackClick()
    fun onCreateCollectionClick()
    fun onCollectionClick(collectionId: Int)
}