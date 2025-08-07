package com.moscow.cineverse.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.moscow.cineverse.navigation.routes.SplashRoute
import com.moscow.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(): ViewModel() {

    private val _startDestination = mutableStateOf<Any?>(null)
    val startDestination = _startDestination

    init {
        getStartDestination()
    }

    private fun getStartDestination() {
        _startDestination.value = SplashRoute
    }

}