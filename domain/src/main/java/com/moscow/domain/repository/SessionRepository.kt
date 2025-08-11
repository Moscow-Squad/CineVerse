package com.moscow.domain.repository

interface SessionRepository {
    suspend fun getSessionId(): String
    suspend fun getSessionExpiration(): String
}

