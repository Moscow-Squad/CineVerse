package com.moscow.domain.usecase.local

import com.moscow.domain.repository.UserRepository
import javax.inject.Inject

class RemoveUserDetailsUseCase @Inject constructor(
    private val  userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.clearUser()
}