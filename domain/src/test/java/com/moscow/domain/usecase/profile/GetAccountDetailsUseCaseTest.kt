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

class GetAccountDetailsUseCaseTest {

    private lateinit var profileRepository: ProfileRepository
    private lateinit var getAccountDetailsUseCase: GetAccountDetailsUseCase

    @BeforeEach
    fun setUp() {
        profileRepository = mockk(relaxed = true)
        getAccountDetailsUseCase = GetAccountDetailsUseCase(profileRepository)
    }

    @Test
    fun `getAccountDetailsUseCase should call repository method`() = runTest {
        // Given
        val accountId = "42"
        val sessionId = "session_1"

        // When
        getAccountDetailsUseCase(accountId, sessionId)

        // Then
        coVerify(exactly = 1) {
            profileRepository.getUserInfo(
                accountId = accountId,
                sessionId = sessionId
            )
        }
    }


    @Test
    fun `getAccountDetailsUseCase makes exactly one repository call`() = runTest {
        // Given
        val accountId = "202"
        val sessionId = "sess_202"

        // When
        getAccountDetailsUseCase(accountId, sessionId)

        // Then
        coVerify(exactly = 1) { profileRepository.getUserInfo(accountId, sessionId) }
        confirmVerified(profileRepository)
    }
}