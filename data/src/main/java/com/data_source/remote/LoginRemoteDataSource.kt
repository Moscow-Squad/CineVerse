package com.data_source.remote

import com.remote.dto.AccountDto
import com.remote.dto.login.GuestSessionDto
import com.remote.dto.login.LoginDto
import com.remote.dto.login.RequestTokenDto
import com.remote.dto.login.SessionDto

interface LoginRemoteDataSource {

    suspend fun createRequestToken(): RequestTokenDto

    suspend fun validateLoginWithRequestToken(
        username: String, password: String, requestToken: String
    ): LoginDto

    suspend fun createAuthenticatedUserSession(
        requestToken: String
    ): SessionDto

    suspend fun createGuestUserSession(): GuestSessionDto

    suspend fun getUserId(
        sessionId: String
    ): AccountDto

}