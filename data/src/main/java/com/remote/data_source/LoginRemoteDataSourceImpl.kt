package com.remote.data_source

import com.data_source.remote.LoginRemoteDataSource
import com.remote.dto.AccountDto
import com.remote.dto.login.GuestSessionDto
import com.remote.dto.login.LoginDto
import com.remote.dto.login.RequestTokenDto
import com.remote.dto.login.SessionDto
import com.remote.services.LoginService
import com.utils.handleApi
import retrofit2.Response
import kotlin.math.log

class LoginRemoteDataSourceImpl(
    private val loginService: LoginService
) : LoginRemoteDataSource {
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

    override suspend fun getUserId(sessionId: String): AccountDto = handleApi {
        loginService.getUserId(sessionId)
    }
}