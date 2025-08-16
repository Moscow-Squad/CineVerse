package com.moscow.cineverse.screen.my_collections

object MyCollectionsEffectHandler {
    fun handleEffect(
        effect: MyCollectionsEvent,
        onNavigateBack: () -> Unit,
        navigateToCreateCollectionDialog: () -> Unit,
        navigateToExplore: () -> Unit,
        navigateToCollectionDetails: (
            collectionId: Int, collectionName: String
        ) -> Unit
    ) {
        when (effect) {
            MyCollectionsEvent.OnNavigateBack -> {
                onNavigateBack()
            }

            is MyCollectionsEvent.OnNavigateToCollection -> {
                navigateToCollectionDetails(effect.collectionId, effect.collectionName)
            }

            MyCollectionsEvent.OnNavigateToCreateCollection -> {
                navigateToCreateCollectionDialog()
            }

            MyCollectionsEvent.OnStartCollecting -> {
                navigateToExplore()
            }
        }
    }
}