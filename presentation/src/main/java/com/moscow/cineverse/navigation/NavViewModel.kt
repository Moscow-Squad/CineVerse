package com.moscow.cineverse.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.navigation.routes.HomeRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.domain.repository.PreferenceRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

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
            if (preferenceRepository.isGuest() && preferenceRepository.isLoggedIn()){
                val isValid = isValidGuestSession(preferenceRepository.getSessionExpiration())
                _startDestination.value = if (isValid) HomeRoute else LoginRoute
            }else{
                val isLoggedIn = preferenceRepository.isLoggedIn()
                _startDestination.value = if (isLoggedIn) HomeRoute else LoginRoute
            }
        }
    }

    private fun isValidGuestSession(expirationDateTime: String): Boolean{
        if (expirationDateTime.isNotEmpty()){
            val iso = expirationDateTime.replace(" UTC", "").replace(' ', 'T') + "Z"
            val expiresAtInstant = Instant.parse(iso)
            val expiresAtMillis = expiresAtInstant.toEpochMilliseconds()
            val now = Clock.System.now().toEpochMilliseconds()
            return now < expiresAtMillis
        }
        return false
    }
}