package com.moscow.cineverse.screen.collections

import com.moscow.cineverse.base.BaseViewModel

class CollectionsViewModel :
    BaseViewModel<CollectionsUiState, CollectionsEffect>(CollectionsUiState()),
    CollectionsInteractionListener {

}