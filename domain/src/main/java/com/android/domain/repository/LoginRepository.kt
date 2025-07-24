package com.android.domain.repository

import com.android.domain.model.LoginData

interface LoginRepository {
    suspend fun loginWithUsernameAndPassword(loginData: LoginData): Boolean
    suspend fun loginAsGuest(): Boolean
}