package com.moscow.domain.usecase.login

import com.moscow.domain.repository.LoginRepository
import javax.inject.Inject

class LoginAsGuestUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.loginAsGuest()
}