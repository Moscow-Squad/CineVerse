package com.moscow.domain.repository.auth

import com.moscow.domain.model.UserInfo

interface ProfileRepository {
    suspend fun getUserInfo(accountId:String, sessionId:String): UserInfo
    suspend fun logout(sessionId: String): Boolean
}