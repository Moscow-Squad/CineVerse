package com.moscow.remote.data_source

import com.moscow.data_source.remote.AccountRemoteDataSource
import com.moscow.remote.dto.profile.AccountDto
import com.moscow.remote.services.AuthenticationService
import com.moscow.utils.handleApi
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val authenticationService: AuthenticationService
) : AccountRemoteDataSource {
    override suspend fun getUserId(sessionId: String): AccountDto = handleApi {
        authenticationService.getUserId(sessionId)
    }
}