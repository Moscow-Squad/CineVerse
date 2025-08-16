package com.moscow.data_source.remote

import com.moscow.remote.dto.login.GuestSessionDto
import com.moscow.remote.dto.login.LoginResponseDto
import com.moscow.remote.dto.login.RequestTokenDto
import com.moscow.remote.dto.login.SessionDto

interface AuthenticationRemoteDataSource {

    suspend fun createRequestToken(): RequestTokenDto

    suspend fun validateLoginWithRequestToken(
        username: String,
        password: String,
        requestToken: String
    ): LoginResponseDto

    suspend fun createAuthenticatedUserSession(
        requestToken: String
    ): SessionDto

    suspend fun createGuestUserSession(): GuestSessionDto
    
}