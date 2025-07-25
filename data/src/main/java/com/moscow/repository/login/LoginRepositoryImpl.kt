package com.moscow.repository.login

import com.android.domain.model.LoginData
import com.android.domain.model.UserType
import com.android.domain.repository.LoginRepository
import com.android.domain.repository.PreferenceRepository
import com.moscow.data_source.remote.LoginRemoteDataSource

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val preferenceRepository: PreferenceRepository
): LoginRepository {

    override suspend fun loginWithUsernameAndPassword(loginData: LoginData): Boolean {
       val requestToken = loginRemoteDataSource.createRequestToken()
       val successLogin =
           loginRemoteDataSource.validateLoginWithRequestToken(loginData.username , loginData.password ,requestToken.requestToken?:"")
       if (!successLogin.success){
           return false
       }
       val sessionId = loginRemoteDataSource.createAuthenticatedUserSession(requestToken.requestToken?:"")
       val userId = loginRemoteDataSource.getUserId(sessionId.sessionId?:"")
       preferenceRepository.saveUser(UserType.AuthenticatedUser(
           id = userId.toString(),
           username = loginData.username,
           sessionId = sessionId.sessionId?:""
       ))
        return true
    }

    override suspend fun loginAsGuest(): Boolean {
       val guestSessionInfo = loginRemoteDataSource.createGuestUserSession()
       if (guestSessionInfo.guestSessionId?.isEmpty() == true){
           return false
       }
       preferenceRepository.saveUser(UserType.GuestUser(
           sessionId = guestSessionInfo.guestSessionId?:"",
           expiredAt = guestSessionInfo.expiresAt?:""
       ))
        return true
    }
}