package com.moscow.domain.usecase.profile

import com.moscow.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(sessionId: String) = profileRepository.logout(sessionId)
}