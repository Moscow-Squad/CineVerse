package com.moscow.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.ProfileRemoteDataSource
import com.moscow.remote.dto.profile.response.AccountDto
import com.moscow.remote.dto.profile.response.LogoutResponseDto
import com.moscow.remote.services.ProfileService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class ProfileRemoteDataSourceImplTest {

    private lateinit var profileService: ProfileService
    private lateinit var dataSource: ProfileRemoteDataSource

    @BeforeEach
    fun setup() {
        profileService = mockk()
        dataSource = ProfileRemoteDataSourceImpl(profileService)
    }

    @Test
    fun `getUserInfo returns body on success`() = runTest {

        val accountId = "acc_123"
        val sessionId = "sess_456"
        val expected = mockk<AccountDto>()
        coEvery { profileService.getUserInfo(accountId, sessionId) } returns Response.success(expected)

        val result = dataSource.getUserInfo(accountId, sessionId)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { profileService.getUserInfo(accountId, sessionId) }
    }

    @Test
    fun `logout returns body on success`() = runTest {

        val sessionId = "sess_logout"
        val expected = mockk<LogoutResponseDto>()
        coEvery { profileService.logout(sessionId) } returns Response.success(expected)

        val result = dataSource.logout(sessionId)

        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { profileService.logout(sessionId) }
    }
}