package com.moscow.cineverse.screen.collection_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.CollectionDetailsRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.domain.model.MediaItem
import com.moscow.domain.model.MediaType
import com.moscow.domain.usecase.collection.DeleteMediaFromCollectionV4UseCase
import com.moscow.domain.usecase.collection.GetCollectionMediaItemsV4UseCase
import kotlinx.coroutines.flow.Flow

class CollectionDetailsViewModel(
    private val deleteMediaFromCollectionV4UseCase: DeleteMediaFromCollectionV4UseCase,
    private val getCollectionMediaItemsV4UseCase: GetCollectionMediaItemsV4UseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CollectionDetailsScreenState, CollectionDetailsEffect>(CollectionDetailsScreenState()),
    CollectionDetailsInteractionListener {

    val collectionId = savedStateHandle.get<Int>(CollectionDetailsRoute.COLLECTION_ID) ?: 0
    val collectionName = savedStateHandle.get<String>(CollectionDetailsRoute.COLLECTION_NAME) ?: ""

    fun getMediaItems(): Flow<PagingData<MediaItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getCollectionMediaItemsV4UseCase(collectionId, page)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    override fun onBackButtonClicked() {
        sendEvent(CollectionDetailsEffect.NavigateBack)
    }

    override fun onMediaItemClicked(
        mediaId: Int,
        mediaType: MediaType
    ) {
        if (mediaType == MediaType.Movie){
            sendEvent(CollectionDetailsEffect.NavigateToMovieDetails(mediaId))
        }
        else if (mediaType == MediaType.Tv){
            sendEvent(CollectionDetailsEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onItemDeletedIconClicked(
        mediaId: Int,
        mediaType: MediaType
    ) {
        updateState { it.copy(isLoading = true, isError = false, errorMsg = "") }
        launchAndForget(
            action = {deleteMediaFromCollectionV4UseCase(collectionId, mediaId, mediaType)},
            onSuccess = { updateState { it.copy(isLoading = false) } },
            onError = { e ->
                updateState { it.copy(isLoading = false, isError = true, errorMsg = e.message.toString()) }
            }
        )
    }

    override fun onTipCancelIconClicked() {
        updateState { it.copy(showTip = false) }
    }
}