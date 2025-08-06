package com.moscow.cineverse.screen.my_collections

interface MyCollectionsInteractionListener {
    fun onBackClick()
    fun onCreateCollectionClick()
    fun onStartCollectingClick()
    fun onCollectionClick(collectionId: Int, collectionName: String)
    fun insertNewCollection(collectionId: Int, collectionName: String)
}