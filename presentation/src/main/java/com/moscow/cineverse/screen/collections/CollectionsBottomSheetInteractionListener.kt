package com.moscow.cineverse.screen.collections

interface CollectionsBottomSheetInteractionListener {
    fun onShowCollectionsBottomSheet(show: Boolean)

    fun onAddNewCollectionClick()

    fun onCollectionClick()

    fun onRefresh()
}