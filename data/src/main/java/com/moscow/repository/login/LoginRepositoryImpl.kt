package com.moscow.repository.login

import com.moscow.data_source.remote.AuthenticationRemoteDataSource
import com.moscow.domain.model.LoginData
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.LoginRepository
import com.moscow.domain.repository.UserRepository
import com.moscow.utils.IMAGES_URL
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val userRepository: UserRepository
) : LoginRepository {

    override suspend fun loginWithUsernameAndPassword(loginData: LoginData): Boolean {
        val requestToken = authenticationRemoteDataSource.createRequestToken()
        val successLogin = authenticationRemoteDataSource.validateLoginWithRequestToken(
            loginData.username, loginData.password, requestToken.requestToken ?: ""
        )
        if (!successLogin.success) {
            return false
        }
        val sessionId =
            authenticationRemoteDataSource.createAuthenticatedUserSession(requestToken.requestToken ?: "")
        val userId = authenticationRemoteDataSource.getUserId(sessionId.sessionId ?: "")

        userRepository.saveUser(
            UserType.AuthenticatedUser(
                id = userId.id.toString(),
                username = loginData.username,
                name = userId.name.orEmpty(),
                sessionId = sessionId.sessionId ?: "",
                image = userId.avatar?.tmdb?.avatarPath?.let { IMAGES_URL + it },
                recentlyCollectionId = /*response.listId ?:*/ 0,

            )
        )
        return true
    }

    override suspend fun loginAsGuest(): Boolean {
        val guestSessionInfo = authenticationRemoteDataSource.createGuestUserSession()
        if (guestSessionInfo.guestSessionId?.isEmpty() == true) {
            return false
        }
        userRepository.saveUser(
            UserType.GuestUser(
                sessionId = guestSessionInfo.guestSessionId ?: "",
                expiredAt = guestSessionInfo.expiresAt ?: ""
            )
        )
        return true
    }
}