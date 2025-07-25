package com.moscow.domain.repository

import com.moscow.domain.model.LoginData

interface LoginRepository {
    suspend fun loginWithUsernameAndPassword(loginData: LoginData): Boolean
    suspend fun loginAsGuest(): Boolean
}