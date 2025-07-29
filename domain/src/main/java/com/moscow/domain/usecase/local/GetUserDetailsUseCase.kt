package com.moscow.domain.usecase.local

import com.moscow.domain.repository.PreferenceRepository

class GetUserDetailsUseCase(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke() = preferenceRepository.getUser()
}