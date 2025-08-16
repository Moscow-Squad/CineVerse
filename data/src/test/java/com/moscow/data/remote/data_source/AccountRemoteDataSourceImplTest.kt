package com.moscow.data.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.AccountRemoteDataSource
import com.moscow.remote.data_source.AccountRemoteDataSourceImpl
import com.moscow.remote.dto.profile.response.AccountDto
import com.moscow.remote.dto.profile.response.Avatar
import com.moscow.remote.dto.profile.response.Gravatar
import com.moscow.remote.dto.profile.response.Tmdb
import com.moscow.remote.services.AuthenticationService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response


class AccountRemoteDataSourceImplTest {
    private lateinit var authenticationService: AuthenticationService
    private lateinit var accountRemoteDataSource: AccountRemoteDataSource

    @BeforeEach
    fun setup() {
        authenticationService = mockk()
        accountRemoteDataSource = AccountRemoteDataSourceImpl(authenticationService)
    }

    @Test
    fun `given sessionId when getUserId succeeds then return account details`() = runTest {
        val sessionId = "test_session_id_123"
        val expected = AccountDto(
            id = 12345L,
            name = "John Doe",
            userName = "johndoe",
            avatar = null,
            success = true,
            statusMessage = null
        )
        coEvery { authenticationService.getUserId(sessionId) } returns Response.success(expected)

        val result = accountRemoteDataSource.getUserId(sessionId)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            authenticationService.getUserId(sessionId)
        }
    }

    @Test
    fun `given sessionId when getUserId succeeds with avatar then return complete account data`() = runTest {
        val sessionId = "session_with_avatar"
        val avatar = Avatar(
            gravatar = Gravatar(hash = "gravatar_hash"),
            tmdb = Tmdb(avatarPath = "/avatar.jpg")
        )
        val expected = AccountDto(
            id = 789L,
            name = "Avatar User",
            userName = "avataruser",
            avatar = avatar,
            success = true,
            statusMessage = "Success"
        )
        coEvery { authenticationService.getUserId(sessionId) } returns Response.success(expected)

        val result = accountRemoteDataSource.getUserId(sessionId)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            authenticationService.getUserId(sessionId)
        }
    }

    @Test
    fun `given sessionId when getUserId succeeds with partial data then return partial account data`() = runTest {
        val sessionId = "partial_session"
        val expected = AccountDto(
            id = null,
            name = null,
            userName = "onlyusername",
            avatar = null,
            success = true,
            statusMessage = null
        )
        coEvery { authenticationService.getUserId(sessionId) } returns Response.success(expected)

        val result = accountRemoteDataSource.getUserId(sessionId)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            authenticationService.getUserId(sessionId)
        }
    }

    @Test
    fun `given sessionId when getUserId succeeds with success false then return error account data`() = runTest {
        val sessionId = "error_session"
        val expected = AccountDto(
            id = null,
            name = null,
            userName = null,
            avatar = null,
            success = false,
            statusMessage = "Authentication failed"
        )
        coEvery { authenticationService.getUserId(sessionId) } returns Response.success(expected)

        val result = accountRemoteDataSource.getUserId(sessionId)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            authenticationService.getUserId(sessionId)
        }
    }
}