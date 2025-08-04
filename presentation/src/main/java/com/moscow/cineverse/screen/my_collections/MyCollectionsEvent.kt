package com.moscow.cineverse.screen.my_collections

sealed interface MyCollectionsEvent {
    data object OnNavigateBack : MyCollectionsEvent
    data object OnNavigateToCreateCollection : MyCollectionsEvent
    data class OnNavigateToCollection(val collectionId: Int) : MyCollectionsEvent
}