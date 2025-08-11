package com.moscow.data_source.remote

import com.moscow.remote.dto.profile.AccountDto

interface AccountRemoteDataSource {
    suspend fun getUserId(
        sessionId: String
    ): AccountDto
}