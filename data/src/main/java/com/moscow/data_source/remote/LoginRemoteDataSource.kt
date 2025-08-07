package com.moscow.data_source.remote

import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.dto.login.GuestSessionDto
import com.moscow.remote.dto.login.LoginDto
import com.moscow.remote.dto.login.RequestTokenDto
import com.moscow.remote.dto.login.SessionDto

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