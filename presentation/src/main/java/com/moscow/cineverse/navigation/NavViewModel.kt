package com.moscow.cineverse.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.domain.repository.PreferenceRepository
import com.moscow.cineverse.navigation.routes.ExploreRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import kotlinx.coroutines.launch

class NavViewModel(
    private val preferenceRepository: PreferenceRepository
): ViewModel() {

    private val _startDestination = mutableStateOf<Any?>(null)
    val startDestination = _startDestination

    init {
        getStartDestination()
    }

    private fun getStartDestination() {
        viewModelScope.launch {
            val isLoggedIn = preferenceRepository.isLoggedIn()
            _startDestination.value = if (isLoggedIn) ExploreRoute else LoginRoute
        }
    }
}