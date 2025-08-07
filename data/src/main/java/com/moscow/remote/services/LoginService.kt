package com.moscow.remote.services

import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.dto.login.GuestSessionDto
import com.moscow.remote.dto.login.LoginDto
import com.moscow.remote.dto.login.RequestTokenDto
import com.moscow.remote.dto.login.SessionDto
import com.moscow.utils.ACCOUNT
import com.moscow.utils.LOGIN
import com.moscow.utils.NEW_GUEST_SESSION
import com.moscow.utils.NEW_SESSION
import com.moscow.utils.NEW_TOKEN
import com.moscow.utils.PASSWORD
import com.moscow.utils.REQUEST_TOKEN
import com.moscow.utils.SESSION_ID_WITH_EQUAL
import com.moscow.utils.USERNAME
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
