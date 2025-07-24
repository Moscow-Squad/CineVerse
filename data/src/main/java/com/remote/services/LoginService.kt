package com.remote.services

import com.remote.dto.AccountDto
import com.remote.dto.login.GuestSessionDto
import com.remote.dto.login.LoginDto
import com.remote.dto.login.RequestTokenDto
import com.remote.dto.login.SessionDto
import com.utils.ACCOUNT
import com.utils.LOGIN
import com.utils.NEW_GUEST_SESSION
import com.utils.NEW_SESSION
import com.utils.NEW_TOKEN
import com.utils.PASSWORD
import com.utils.REQUEST_TOKEN
import com.utils.SESSION_ID_WITH_EQUAL
import com.utils.USERNAME
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @GET(NEW_TOKEN)
    suspend fun createRequestToken(): Response<RequestTokenDto>

    @POST(LOGIN)
    suspend fun validateLoginWithRequestToken(
        @Query(USERNAME) username: String,
        @Query(PASSWORD) password: String,
        @Query(REQUEST_TOKEN) requestToken: String
    ): Response<LoginDto>

    @POST(NEW_SESSION)
    suspend fun createAuthenticatedUserSession(
        @Query(REQUEST_TOKEN) requestToken: String
    ): Response<SessionDto>

    @GET(NEW_GUEST_SESSION)
    suspend fun createGuestUserSession(): Response<GuestSessionDto>

    @GET(ACCOUNT)
    suspend fun getUserId(
        @Query(SESSION_ID_WITH_EQUAL) sessionId: String
    ): Response<AccountDto>

}