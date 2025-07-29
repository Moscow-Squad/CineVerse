package com.moscow.repository.login

import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.data_source.remote.LoginRemoteDataSource
import com.moscow.domain.model.LoginData
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.LoginRepository
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.remote.dto.CreateCollectionDto
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val collectionRemoteDataSource: CollectionRemoteDataSource,
    private val preferenceRepository: PreferenceRepository
) : LoginRepository {

    override suspend fun loginWithUsernameAndPassword(loginData: LoginData): Boolean {
        val requestToken = loginRemoteDataSource.createRequestToken()
        val successLogin = loginRemoteDataSource.validateLoginWithRequestToken(
            loginData.username, loginData.password, requestToken.requestToken ?: ""
        )
        if (!successLogin.success) {
            return false
        }
        val sessionId =
            loginRemoteDataSource.createAuthenticatedUserSession(requestToken.requestToken ?: "")
        val userId = loginRemoteDataSource.getUserId(sessionId.sessionId ?: "")
        val response  = collectionRemoteDataSource.addNewCollection(
            CreateCollectionDto(
                name = "Recently Viewed",
                description = "A personalized list for recently viewed items"
            ), sessionId.sessionId ?: ""
        )
        preferenceRepository.saveUser(
            UserType.AuthenticatedUser(
                id = userId.toString(),
                username = loginData.username,
                sessionId = sessionId.sessionId ?: "",
                recentlyCollectionId = response.listId ?: 0
            )
        )
        return true
    }

    override suspend fun loginAsGuest(): Boolean {
        val guestSessionInfo = loginRemoteDataSource.createGuestUserSession()
        if (guestSessionInfo.guestSessionId?.isEmpty() == true) {
            return false
        }
        preferenceRepository.saveUser(
            UserType.GuestUser(
                sessionId = guestSessionInfo.guestSessionId ?: "",
                expiredAt = guestSessionInfo.expiresAt ?: ""
            )
        )
        return true
    }
}