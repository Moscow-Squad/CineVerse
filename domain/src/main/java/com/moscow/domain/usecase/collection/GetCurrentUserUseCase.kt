package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.PreferenceRepository
import jakarta.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend fun isLoggedIn(): Boolean = preferenceRepository.isLoggedIn()
}