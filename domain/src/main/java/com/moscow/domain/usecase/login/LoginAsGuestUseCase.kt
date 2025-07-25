package com.moscow.domain.usecase.login

import com.moscow.domain.repository.LoginRepository

class LoginAsGuestUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.loginAsGuest()
}