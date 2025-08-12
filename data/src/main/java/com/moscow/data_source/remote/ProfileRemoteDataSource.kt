package com.moscow.data_source.remote

import com.moscow.remote.dto.profile.response.AccountDto
import com.moscow.remote.dto.profile.response.LogoutResponseDto

interface ProfileRemoteDataSource {
    suspend fun getUserInfo(accountId:String, sessionId:String): AccountDto
    suspend fun logout(sessionId: String): LogoutResponseDto
}