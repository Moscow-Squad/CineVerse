package com.moscow.repository

import com.moscow.data_source.remote.ProfileRemoteDataSource
import com.moscow.domain.model.profile.AccountDetails
import com.moscow.domain.repository.ProfileRepository
import com.moscow.mapper.toDomain
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
) : ProfileRepository{
    override suspend fun getUserInfo(
        accountId: String,
        sessionId: String
    ): AccountDetails  =
       profileRemoteDataSource.getUserInfo(accountId,sessionId).toDomain()


    override suspend fun logout(sessionId: String): Boolean =
        profileRemoteDataSource.logout(sessionId).success != false
}