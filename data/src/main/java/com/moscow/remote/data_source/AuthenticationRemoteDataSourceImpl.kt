package com.moscow.remote.data_source

import com.moscow.data_source.remote.AuthenticationRemoteDataSource
import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.dto.login.GuestSessionDto
import com.moscow.remote.dto.login.LoginDto
import com.moscow.remote.dto.login.RequestTokenDto
import com.moscow.remote.dto.login.SessionDto
import com.moscow.remote.services.LoginService
import com.moscow.utils.handleApi
import javax.inject.Inject


class AuthenticationRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : AuthenticationRemoteDataSource {
    override suspend fun createRequestToken(): RequestTokenDto = handleApi {
        loginService.createRequestToken()
    }

    override suspend fun validateLoginWithRequestToken(
        username: String, password: String, requestToken: String
    ): LoginDto = handleApi {
        loginService.validateLoginWithRequestToken(username, password, requestToken)
    }

    override suspend fun createAuthenticatedUserSession(requestToken: String): SessionDto =
        handleApi {
            loginService.createAuthenticatedUserSession(requestToken)
        }

    override suspend fun createGuestUserSession(): GuestSessionDto = handleApi {
        loginService.createGuestUserSession()
    }


}