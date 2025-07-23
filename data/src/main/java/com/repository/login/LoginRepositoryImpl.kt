package com.repository.login

import com.android.domain.model.LoginData
import com.android.domain.model.UserType
import com.android.domain.repository.LoginRepository
import com.android.domain.repository.PreferenceRepository
import com.remote.source.LoginRemoteDataSource

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val preferenceRepository: PreferenceRepository
): LoginRepository {

    override suspend fun loginWithUsernameAndPassword(loginData: LoginData): Boolean {
//        val requestToken = loginRemoteDataSource.createRequestToken()
//        val successLogin = loginRemoteDataSource.validateLoginWithRequestToken(requestToken,loginData)
//        if (!successLogin){
//            return false
//        }
//        val sessionId = loginRemoteDataSource.createAuthenticatedUserSession(requestToken)
//        val userId = loginRemoteDataSource.getUserId(sessionId)
//        preferenceRepository.saveUser(UserType.AuthenticatedUser(
//            id = userId.toString(),
//            username = loginData.username,
//            sessionId = sessionId
//        ))
        return true
    }

    override suspend fun loginAsGuest(): Boolean {
//        val guestSessionInfo = loginRemoteDataSource.createGuestUserSession()
//        if (guestSessionInfo.first.isEmpty()){
//            return false
//        }
//        preferenceRepository.saveUser(UserType.GuestUser(
//            sessionId = guestSessionInfo.first,
//            expiredAt = guestSessionInfo.second
//        ))
        return true
    }
}