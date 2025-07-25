package com.moscow.domain.usecase.login

import com.moscow.domain.model.LoginData
import com.moscow.domain.repository.LoginRepository

class LoginWithUsernameAndPasswordUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(loginData: LoginData) =
        loginRepository.loginWithUsernameAndPassword(loginData)
}