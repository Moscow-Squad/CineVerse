package com.moscow.domain.usecase.login

import com.moscow.domain.repository.auth.LoginRepository
import javax.inject.Inject

class LoginWithUsernameAndPasswordUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ) = loginRepository.loginWithUsernameAndPassword(
        username = username,
        password = password
    )
}