package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke() = preferenceRepository.isLoggedIn()

}