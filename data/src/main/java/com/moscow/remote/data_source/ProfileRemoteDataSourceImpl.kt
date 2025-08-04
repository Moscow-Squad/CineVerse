package com.moscow.remote.data_source

import com.moscow.data_source.remote.ProfileRemoteDataSource
import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.dto.profile.LogoutDto
import com.moscow.remote.dto.profile.LogoutRequestDto
import com.moscow.remote.services.ProfileService
import com.moscow.utils.handleApi
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val profileService: ProfileService
): ProfileRemoteDataSource {

    override suspend fun getUserInfo(
        accountId: String,
        sessionId: String
    ): AccountDto = handleApi {
        profileService.getUserInfo(accountId,sessionId)
    }

    override suspend fun logout(sessionId: String): LogoutDto  = handleApi {
        profileService.logout(LogoutRequestDto(sessionId))
    }
}