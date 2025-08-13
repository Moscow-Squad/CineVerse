package com.moscow.domain.usecase.profile

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.auth.ProfileRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogoutUseCaseTest {

    private lateinit var profileRepository: ProfileRepository
    private lateinit var logoutUseCase: LogoutUseCase

    @BeforeEach
    fun setUp() {
        profileRepository = mockk(relaxed = true)
        logoutUseCase = LogoutUseCase(profileRepository)
    }

    @Test
    fun `logoutUseCase should call repository method`() = runTest {
        // Given
        val sessionId = "session_1"

        // When
        logoutUseCase(sessionId)

        // Then
        coVerify(exactly = 1) { profileRepository.logout(sessionId = sessionId) }
    }

    @Test
    fun `logoutUseCase should return result from repository`() = runTest {
        // Given
        val sessionId = "session_2"
        val expected = true
        coEvery { profileRepository.logout(any()) } returns expected

        // When
        val result = logoutUseCase(sessionId)

        // Then
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { profileRepository.logout(sessionId = sessionId) }
    }

    @Test
    fun `logoutUseCase should complete operation successfully`() = runTest {
        // Given
        val sessionId = "session_success"
        coEvery { profileRepository.logout(sessionId) } returns true

        // When
        val result = logoutUseCase(sessionId)

        // Then
        assertThat(result).isTrue()
        coVerify(exactly = 1) { profileRepository.logout(sessionId = sessionId) }
    }

    @Test
    fun `logoutUseCase should handle multiple invocations`() = runTest {
        // Given
        val base = "s"

        // When
        repeat(3) { i -> logoutUseCase("$base-$i") }

        // Then
        coVerify(exactly = 1) { profileRepository.logout(sessionId = "s-0") }
        coVerify(exactly = 1) { profileRepository.logout(sessionId = "s-1") }
        coVerify(exactly = 1) { profileRepository.logout(sessionId = "s-2") }
    }

    @Test
    fun `logoutUseCase makes exactly one repository call`() = runTest {
        // Given
        val sessionId = "single_call"

        // When
        logoutUseCase(sessionId)

        // Then
        coVerify(exactly = 1) { profileRepository.logout(sessionId = sessionId) }
        confirmVerified(profileRepository)
    }

    @Test
    fun `logoutUseCase passes the correct sessionId`() = runTest {
        // Given
        val sessionId = "token-123"

        // When
        logoutUseCase(sessionId)

        // Then
        coVerify(exactly = 1) { profileRepository.logout(sessionId = "token-123") }
    }

    @Test
    fun `logoutUseCase should return false when repository returns false`() = runTest {
        // Given
        val sessionId = "session_fail"
        coEvery { profileRepository.logout(sessionId) } returns false

        // When
        val result = logoutUseCase(sessionId)

        // Then
        assertThat(result).isFalse()
        coVerify(exactly = 1) { profileRepository.logout(sessionId = sessionId) }
    }
}