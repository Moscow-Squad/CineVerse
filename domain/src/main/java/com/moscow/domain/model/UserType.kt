package com.moscow.domain.model

sealed class UserType {
    data class AuthenticatedUser(
        val id: String,
        val username: String,
        val sessionId: String
    ) : UserType()

    data class GuestUser(
        val sessionId: String,
        val expiredAt: String
    ) : UserType()
}
