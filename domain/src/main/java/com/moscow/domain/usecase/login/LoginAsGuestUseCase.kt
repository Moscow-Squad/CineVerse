package com.moscow.domain.usecase.login

import com.moscow.domain.repository.auth.LoginRepository
import javax.inject.Inject

class LoginAsGuestUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.loginAsGuest()
}