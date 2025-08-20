package com.moscow.repository.login

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.AccountRemoteDataSource
import com.moscow.data_source.remote.AuthenticationRemoteDataSource
import com.moscow.domain.model.UserType
import com.moscow.remote.dto.login.GuestSessionDto
import com.moscow.remote.dto.login.LoginResponseDto
import com.moscow.remote.dto.login.RequestTokenDto
import com.moscow.remote.dto.login.SessionDto
import com.moscow.remote.dto.profile.response.AccountDto
import com.moscow.remote.dto.profile.response.Avatar
import com.moscow.remote.dto.profile.response.Tmdb
import com.moscow.utils.IMAGES_URL
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SessionManagerTest {

    private lateinit var authRemote: AuthenticationRemoteDataSource
    private lateinit var accountRemote: AccountRemoteDataSource
    private lateinit var sessionManager: SessionManager

    @BeforeEach
    fun setup() {
        authRemote = mockk()
        accountRemote = mockk()
        sessionManager = SessionManager(authRemote, accountRemote)
    }

    @Test
    fun `authenticateUser returns session when login is successful`() = runTest {
        val token = "token123"
        val session = SessionDto(success = true, sessionId = "session456")

        coEvery { authRemote.createRequestToken() } returns RequestTokenDto(success = true, requestToken = token)
        coEvery { authRemote.validateLoginWithRequestToken("user", "pass", token) } returns LoginResponseDto(success = true)
        coEvery { authRemote.createAuthenticatedUserSession(token) } returns session

        val result = sessionManager.authenticateUser("user", "pass")

        assertThat(result).isEqualTo(session)
    }

    @Test
    fun `authenticateUser returns null when token is null`() = runTest {
        coEvery { authRemote.createRequestToken() } returns RequestTokenDto(success = true, requestToken = null)

        val result = sessionManager.authenticateUser("user", "pass")

        assertThat(result).isNull()
    }

    @Test
    fun `authenticateUser returns null when validation fails`() = runTest {
        val token = "token123"

        coEvery { authRemote.createRequestToken() } returns RequestTokenDto(success = true, requestToken = token)
        coEvery { authRemote.validateLoginWithRequestToken("user", "pass", token) } returns LoginResponseDto(success = false)

        val result = sessionManager.authenticateUser("user", "pass")

        assertThat(result).isNull()
    }

    @Test
    fun `fetchUser returns AuthenticatedUser with correct data`() = runTest {
        val session = SessionDto(success = true, sessionId = "session456")
        val avatarPath = "/avatar.png"
        val accountDto = AccountDto(
            id = 1L,
            userName = "Ahmed",
            name = "Ahmed M.",
            avatar = Avatar(tmdb = Tmdb(avatarPath = avatarPath))
        )

        coEvery { accountRemote.getUserId("session456") } returns accountDto

        val result = sessionManager.fetchUser(session)

        assertThat(result).isEqualTo(
            UserType.AuthenticatedUser(
                id = "1",
                username = "Ahmed",
                name = "Ahmed M.",
                sessionId = "session456",
                image = IMAGES_URL + avatarPath,
                recentlyCollectionId = 0
            )
        )
    }

    @Test
    fun `fetchUser returns null when sessionId is null`() = runTest {
        val session = SessionDto(success = true, sessionId = null)

        val result = sessionManager.fetchUser(session)

        assertThat(result).isNull()
    }

    @Test
    fun `createGuestSession returns null when sessionId is empty`() = runTest {
        val guestDto = GuestSessionDto(success = true, guestSessionId = "", statusMessage = "2025-12-31")

        coEvery { authRemote.createGuestUserSession() } returns guestDto

        val result = sessionManager.createGuestSession()

        assertThat(result).isNull()
    }
}