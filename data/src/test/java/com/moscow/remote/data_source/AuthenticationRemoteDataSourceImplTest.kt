package com.moscow.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.AuthenticationRemoteDataSource
import com.moscow.remote.data_source.AuthenticationRemoteDataSourceImpl
import com.moscow.remote.dto.login.GuestSessionDto
import com.moscow.remote.services.AuthenticationService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.moscow.remote.dto.login.LoginResponseDto
import com.moscow.remote.dto.login.RequestTokenDto
import com.moscow.remote.dto.login.SessionDto
import retrofit2.Response

class AuthenticationRemoteDataSourceImplTest {

    private lateinit var authenticationService: AuthenticationService
    private lateinit var authenticationRemoteDataSource: AuthenticationRemoteDataSource

    @BeforeEach
    fun setup() {
        authenticationService = mockk()
        authenticationRemoteDataSource =
            AuthenticationRemoteDataSourceImpl(authenticationService)
    }

    @Test
    fun `createRequestToken returns body on success`() = runTest {

        val expected = mockk<RequestTokenDto>()
        coEvery { authenticationService.createRequestToken() } returns Response.success(expected)

        val result = authenticationRemoteDataSource.createRequestToken()

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { authenticationService.createRequestToken() }
    }

    @Test
    fun `validateLoginWithRequestToken returns body on success`() = runTest {

        val username = "john"
        val password = "doe123"
        val requestToken = "req_token_123"
        val expected = mockk<LoginResponseDto>()
        coEvery {
            authenticationService.validateLoginWithRequestToken(username, password, requestToken)
        } returns Response.success(expected)

        val result = authenticationRemoteDataSource.validateLoginWithRequestToken(
            username = username,
            password = password,
            requestToken = requestToken
        )

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            authenticationService.validateLoginWithRequestToken(username, password, requestToken)
        }
    }

    @Test
    fun `createAuthenticatedUserSession returns body on success`() = runTest {

        val requestToken = "req_token_456"
        val expected = mockk<SessionDto>()
        coEvery {
            authenticationService.createAuthenticatedUserSession(requestToken)
        } returns Response.success(expected)

        val result =
            authenticationRemoteDataSource.createAuthenticatedUserSession(requestToken)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            authenticationService.createAuthenticatedUserSession(requestToken)
        }
    }

    @Test
    fun `createGuestUserSession returns body on success`() = runTest {

        val expected = mockk<GuestSessionDto>()
        coEvery { authenticationService.createGuestUserSession() } returns Response.success(expected)

        val result = authenticationRemoteDataSource.createGuestUserSession()

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { authenticationService.createGuestUserSession() }
    }
}