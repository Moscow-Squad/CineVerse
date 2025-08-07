package com.moscow.cineverse.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.navigation.routes.HomeRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.cineverse.navigation.routes.OnBoardingRoute
import com.moscow.cineverse.navigation.routes.SplashRoute
import com.moscow.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
): ViewModel() {

    private val _startDestination = mutableStateOf<Any?>(null)
    val startDestination = _startDestination

    init {
        getStartDestination()
    }

    private fun getStartDestination() = SplashRoute

}