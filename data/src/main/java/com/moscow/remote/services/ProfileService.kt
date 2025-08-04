package com.moscow.remote.services

import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.dto.profile.LogoutDto
import com.moscow.remote.dto.profile.LogoutRequestDto
import com.moscow.utils.ACCOUNT
import com.moscow.utils.AUTHENTICATION
import com.moscow.utils.SESSION_ID
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {

    @GET("$ACCOUNT/{account_id}")
    suspend fun getUserInfo(
        @Path("account_id") accountId:String,
        @Query(SESSION_ID) sessionId:String,
    ): Response<AccountDto>

    @DELETE("${AUTHENTICATION}session")
    suspend fun logout(
        @Body logoutRequestDto: LogoutRequestDto
    ): Response<LogoutDto>
}