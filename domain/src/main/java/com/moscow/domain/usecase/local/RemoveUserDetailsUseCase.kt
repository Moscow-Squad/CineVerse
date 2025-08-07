package com.moscow.domain.usecase.local

import com.moscow.domain.repository.PreferenceRepository
import javax.inject.Inject

class RemoveUserDetailsUseCase @Inject constructor(
    private val  preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke() = preferenceRepository.clearUser()
}