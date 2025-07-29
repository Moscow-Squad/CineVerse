package com.moscow.domain.usecase.login

import com.moscow.domain.model.LoginData
import com.moscow.domain.repository.LoginRepository
import javax.inject.Inject

class LoginWithUsernameAndPasswordUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(loginData: LoginData) =
        loginRepository.loginWithUsernameAndPassword(loginData)
}