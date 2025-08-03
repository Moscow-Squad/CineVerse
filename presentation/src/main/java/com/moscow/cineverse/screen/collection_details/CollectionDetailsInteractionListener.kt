package com.moscow.cineverse.screen.collection_details

import com.moscow.domain.model.MediaType

interface CollectionDetailsInteractionListener {
    fun onBackButtonClicked()
    fun onMediaItemClicked(mediaId: Int, mediaType: MediaType)
    fun onItemDeletedIconClicked(mediaId: Int, mediaType: MediaType)
    fun onTipCancelIconClicked()
}