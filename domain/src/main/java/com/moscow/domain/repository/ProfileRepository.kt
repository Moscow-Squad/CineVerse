package com.moscow.domain.repository

import com.moscow.domain.model.profile.AccountDetails

interface ProfileRepository {

    suspend fun getUserInfo(accountId:String, sessionId:String): AccountDetails

    suspend fun logout(sessionId: String): Boolean
}