package com.moscow.cineverse.screen.collection_details

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.domain.usecase.collection.AddMediaToCollectionV4UseCase
import com.moscow.domain.usecase.collection.DeleteMediaFromCollectionV4UseCase
import com.moscow.domain.usecase.collection.GetCollectionMediaItemsV4UseCase

class CollectionDetailsViewModel(
    private val addMediaToCollectionV4UseCase: AddMediaToCollectionV4UseCase,
    private val deleteMediaFromCollectionV4UseCase: DeleteMediaFromCollectionV4UseCase,
    private val getCollectionMediaItemsV4UseCase: GetCollectionMediaItemsV4UseCase
) : BaseViewModel<CollectionDetailsScreenState, CollectionDetailsEffect>(CollectionDetailsScreenState()) {
}