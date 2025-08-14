package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.auth.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.isLoggedIn()
}