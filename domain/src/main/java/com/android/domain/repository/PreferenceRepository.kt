package com.android.domain.repository

import com.android.domain.model.UserType

interface PreferenceRepository {
    suspend fun saveUser(userType: UserType)
    suspend fun getUser(): UserType
    suspend fun clearUser()
    suspend fun isGuest(): Boolean
    suspend fun areLoggedIn(): Boolean
}