package com.moscow.domain.repository

import com.moscow.domain.model.UserType

interface PreferenceRepository {
    suspend fun saveUser(userType: UserType)
    suspend fun getUser(): UserType
    suspend fun getSessionId(): String
    suspend fun getSessionExpiration(): String
    suspend fun clearUser()
    suspend fun isGuest(): Boolean
    suspend fun isLoggedIn(): Boolean
}