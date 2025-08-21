package com.moscow.cineverse

import com.moscow.cineverse.navigation.AppDestination
import com.moscow.cineverse.navigation.routes.HomeRoute
import com.moscow.cineverse.navigation.routes.LoginRoute
import com.moscow.cineverse.navigation.routes.OnBoardingRoute
import com.moscow.domain.repository.OnboardingRepository
import com.moscow.domain.repository.auth.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDecider @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val userRepository: UserRepository
) {
    suspend fun determineDestination(): AppDestination = coroutineScope {
        // Parallel operations for speed
        val onboardingDeferred = async { onboardingRepository.isOnBoardingCompleted() }
        val guestDeferred = async { userRepository.isGuest() }
        val loggedInDeferred = async { userRepository.isLoggedIn() }

        val isOnboardingCompleted = onboardingDeferred.await()
        if (!isOnboardingCompleted) return@coroutineScope OnBoardingRoute

        val isGuest = guestDeferred.await()
        if (isGuest) {
            val sessionExpiration = userRepository.getSessionExpiration()
            return@coroutineScope if (isValidGuestSession(sessionExpiration)) HomeRoute else LoginRoute
        }

        val isLoggedIn = loggedInDeferred.await()
        if (!isLoggedIn) return@coroutineScope LoginRoute

        return@coroutineScope HomeRoute
    }


    private fun isValidGuestSession(expirationDateTime: String): Boolean {
        if (expirationDateTime.isEmpty()) return false

        val iso = expirationDateTime.replace(" UTC", "").replace(' ', 'T') + "Z"
        return runCatching {
            val now = Clock.System.now().toEpochMilliseconds()
            val expiresAt = Instant.parse(iso).toEpochMilliseconds()
            now < expiresAt
        }.getOrDefault(false)
    }
}