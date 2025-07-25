package com.moscow.repository.login

import com.moscow.data_source.remote.LoginRemoteDataSource
import com.moscow.domain.model.LoginData
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.LoginRepository
import com.moscow.domain.repository.PreferenceRepository

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