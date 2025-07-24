package com.android.domain.usecase.login

import com.android.domain.model.LoginData
import com.android.domain.repository.LoginRepository

class LoginWithUsernameAndPasswordUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(loginData: LoginData) =
        loginRepository.loginWithUsernameAndPassword(loginData)
}