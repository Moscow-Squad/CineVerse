package com.moscow.repository.login

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.auth.LoginRepository
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.remote.dto.login.SessionDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginRepositoryImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var repository: LoginRepository

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        sessionManager = mockk()
        repository = LoginRepositoryImpl(userRepository, sessionManager)
    }

    @Test
    fun `loginWithUsernameAndPassword returns true on successful authentication and user save`() = runTest {
        val username = "testuser"
        val password = "testpass"
        val sessionDto = SessionDto(success = true, sessionId = "session123")
        val authenticatedUser = UserType.AuthenticatedUser(
            id = "1",
            name = "Test User",
            username = username,
            sessionId = "session123",
            image = null,
            recentlyCollectionId = 0
        )

        coEvery { sessionManager.authenticateUser(username, password) } returns sessionDto
        coEvery { sessionManager.fetchUser(sessionDto) } returns authenticatedUser
        coEvery { userRepository.saveUser(authenticatedUser) } returns Unit

        val result = repository.loginWithUsernameAndPassword(username, password)

        assertThat(result).isTrue()
        coVerify(exactly = 1) { sessionManager.authenticateUser(username, password) }
        coVerify(exactly = 1) { sessionManager.fetchUser(sessionDto) }
        coVerify(exactly = 1) { userRepository.saveUser(authenticatedUser) }
    }

    @Test
    fun `loginWithUsernameAndPassword returns false when authentication fails`() = runTest {
        val username = "wronguser"
        val password = "wrongpass"

        coEvery { sessionManager.authenticateUser(username, password) } returns null

        val result = repository.loginWithUsernameAndPassword(username, password)

        assertThat(result).isFalse()
        coVerify(exactly = 1) { sessionManager.authenticateUser(username, password) }
        coVerify(exactly = 0) { sessionManager.fetchUser(any()) }
        coVerify(exactly = 0) { userRepository.saveUser(any()) }
    }

    @Test
    fun `loginWithUsernameAndPassword returns false when fetchUser fails`() = runTest {
        val username = "testuser"
        val password = "testpass"
        val sessionDto = SessionDto(success = true, sessionId = "session123")

        coEvery { sessionManager.authenticateUser(username, password) } returns sessionDto
        coEvery { sessionManager.fetchUser(sessionDto) } returns null

        val result = repository.loginWithUsernameAndPassword(username, password)

        assertThat(result).isFalse()
        coVerify(exactly = 1) { sessionManager.authenticateUser(username, password) }
        coVerify(exactly = 1) { sessionManager.fetchUser(sessionDto) }
        coVerify(exactly = 0) { userRepository.saveUser(any()) }
    }

    @Test
    fun `loginAsGuest returns true on successful guest session creation and user save`() = runTest {
        val guestUser = UserType.GuestUser(
            sessionId = "guest_session123",
            expiredAt = "2024-12-31T23:59:59Z"
        )

        coEvery { sessionManager.createGuestSession() } returns guestUser
        coEvery { userRepository.saveUser(guestUser) } returns Unit

        val result = repository.loginAsGuest()

        assertThat(result).isTrue()
        coVerify(exactly = 1) { sessionManager.createGuestSession() }
        coVerify(exactly = 1) { userRepository.saveUser(guestUser) }
    }

    @Test
    fun `loginAsGuest returns false when guest session creation fails`() = runTest {
        coEvery { sessionManager.createGuestSession() } returns null

        val result = repository.loginAsGuest()

        assertThat(result).isFalse()
        coVerify(exactly = 1) { sessionManager.createGuestSession() }
        coVerify(exactly = 0) { userRepository.saveUser(any()) }
    }
}