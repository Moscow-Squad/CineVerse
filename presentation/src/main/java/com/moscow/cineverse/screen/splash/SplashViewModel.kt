package com.moscow.cineverse.screen.splash

import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.domain.repository.OnboardingRepository
import com.moscow.domain.repository.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val onboardingRepository: OnboardingRepository,
    val userRepository: UserRepository
) : BaseViewModel<Unit, SplashEvent>(Unit){

     fun getDestination() {

         viewModelScope.launch {

             if (!onboardingRepository.isOnBoardingCompleted()){
                sendEvent(SplashEvent.NavigateToOnboarding)
            }else if (userRepository.isGuest()){

                val isValid = isValidGuestSession(userRepository.getSessionExpiration())
                if (isValid) sendEvent(SplashEvent.NavigateToHome) else sendEvent(SplashEvent.NavigateToLogin)
            }else{

                val isLoggedIn = userRepository.isLoggedIn()
                 if (isLoggedIn)  sendEvent(SplashEvent.NavigateToHome) else sendEvent(SplashEvent.NavigateToLogin)
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