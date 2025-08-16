package com.moscow.data_source.remote

import com.moscow.remote.dto.profile.response.AccountDto

interface AccountRemoteDataSource {
    suspend fun getUserId(
        sessionId: String
    ): AccountDto
}