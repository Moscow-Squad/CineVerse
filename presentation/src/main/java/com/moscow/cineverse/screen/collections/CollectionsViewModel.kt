package com.moscow.cineverse.screen.collections

import com.moscow.cineverse.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor():
    BaseViewModel<CollectionsUiState, CollectionsEffect>(CollectionsUiState()),
    CollectionsInteractionListener {

}