package com.android.domain.usecase.login

import com.android.domain.repository.LoginRepository

class LoginAsGuestUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.loginAsGuest()
}