package com.moscow.domain.usecase.preference

import com.moscow.domain.repository.auth.UserRepository
import javax.inject.Inject

class RemoveUserDetailsUseCase @Inject constructor(
    private val  userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.clearUser()
}