package com.moscow.cineverse.screen.collection_details

sealed class CollectionDetailsEffect {
    data object NavigateBack: CollectionDetailsEffect()
}