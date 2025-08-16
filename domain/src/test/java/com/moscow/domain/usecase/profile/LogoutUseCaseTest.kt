package com.moscow.domain.usecase.profile

import com.moscow.domain.repository.auth.ProfileRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogoutUseCaseTest {

    private lateinit var profileRepository: ProfileRepository
    private lateinit var useCase: LogoutUseCase

    @BeforeEach
    fun setup() {
        profileRepository = mockk()
        useCase = LogoutUseCase(profileRepository)
    }

    @Test
    fun `invoke should call logout with sessionId`() = runTest {
        val sessionId = "session_123"

        coEvery { profileRepository.logout(sessionId) } returns true

        useCase(sessionId)

        coVerify(exactly = 1) { profileRepository.logout(sessionId) }
    }
}