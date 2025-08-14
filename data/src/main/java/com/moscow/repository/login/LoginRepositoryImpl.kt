package com.moscow.repository.login

import com.moscow.domain.repository.auth.LoginRepository
import com.moscow.domain.repository.auth.UserRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : LoginRepository {

    override suspend fun loginWithUsernameAndPassword(
        username: String,
        password: String
    ): Boolean {
        val session = sessionManager.authenticateUser(
            username = username,
            password = password
        ) ?: return false

        val user = sessionManager.fetchUser(session) ?: return false

        userRepository.saveUser(user)
        return true
    }

    override suspend fun loginAsGuest(): Boolean {
        val guestSession = sessionManager.createGuestSession() ?: return false
        userRepository.saveUser(guestSession)
        return true
    }
}