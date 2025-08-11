package com.moscow.domain.repository.auth

import com.moscow.domain.model.UserType

interface UserRepository {
    suspend fun saveUser(userType: UserType)
    suspend fun getUser(): UserType
    suspend fun getSessionId(): String
    suspend fun getSessionExpiration(): String
    suspend fun clearUser()
    suspend fun isGuest(): Boolean
    suspend fun isLoggedIn(): Boolean
}