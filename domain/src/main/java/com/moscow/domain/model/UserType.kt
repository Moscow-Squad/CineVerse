package com.moscow.domain.model

sealed class UserType {
    data class AuthenticatedUser(
        val id: String,
        val name:String,
        val username: String,
        val sessionId: String,
        val image:String?,
        val recentlyCollectionId: Int
    ) : UserType()

    data class GuestUser(
        val sessionId: String,
        val expiredAt: String
    ) : UserType()
}
