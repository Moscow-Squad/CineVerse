package com.moscow.repository.login

import com.moscow.data_source.remote.AccountRemoteDataSource
import com.moscow.data_source.remote.AuthenticationRemoteDataSource
import com.moscow.domain.model.UserType
import com.moscow.remote.dto.login.SessionDto
import com.moscow.utils.IMAGES_URL
import jakarta.inject.Inject

class SessionManager @Inject constructor(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource
) {
    suspend fun authenticateUser(username: String, password: String): SessionDto? {
        val token = authenticationRemoteDataSource.createRequestToken().requestToken ?: return null
        val success = authenticationRemoteDataSource.validateLoginWithRequestToken(
            username,
            password,
            token
        ).success
        if (!success) return null
        return authenticationRemoteDataSource.createAuthenticatedUserSession(token)
    }

    suspend fun fetchUser(session: SessionDto): UserType.AuthenticatedUser? {

        val userId = accountRemoteDataSource.getUserId(
            session.sessionId ?: return null
        )

        return UserType.AuthenticatedUser(
            id = userId.id.toString(),
            username = userId.userName.orEmpty(),
            name = userId.name.orEmpty(),
            sessionId = session.sessionId,
            image = userId.avatar?.tmdb?.avatarPath?.let { IMAGES_URL + it },
            recentlyCollectionId = 0
        )
    }

    suspend fun createGuestSession(): UserType.GuestUser? {
        val guest = authenticationRemoteDataSource.createGuestUserSession()
        return if (guest.guestSessionId.isNullOrEmpty()) {
            null
        } else {
            UserType.GuestUser(
                sessionId = guest.guestSessionId,
                expiredAt = guest.expiresAt.orEmpty()
            )
        }
    }
}
