package com.moscow.data_source.remote

import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.dto.profile.LogoutDto

interface ProfileRemoteDataSource {

    suspend fun getUserInfo(accountId:String, sessionId:String): AccountDto

   suspend fun logout(sessionId: String): LogoutDto
}