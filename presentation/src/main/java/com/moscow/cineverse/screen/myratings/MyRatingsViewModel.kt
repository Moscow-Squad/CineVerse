package com.moscow.cineverse.screen.myratings

import com.moscow.cineverse.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRatingsViewModel @Inject constructor() :
    BaseViewModel<MyRatingsUiState, MyRatingsEffect>(MyRatingsUiState()),
    MyRatingsInteractionListener {

}