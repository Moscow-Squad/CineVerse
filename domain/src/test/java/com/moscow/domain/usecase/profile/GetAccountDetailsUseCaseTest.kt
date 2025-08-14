package com.moscow.domain.usecase.profile

import com.moscow.domain.model.UserInfo
import com.moscow.domain.repository.auth.ProfileRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAccountDetailsUseCaseTest {

    private lateinit var profileRepository: ProfileRepository
    private lateinit var useCase: GetAccountDetailsUseCase

    @BeforeEach
    fun setup() {
        profileRepository = mockk()
        useCase = GetAccountDetailsUseCase(profileRepository)
    }

    @Test
    fun `invoke should return account details`() = runTest {
        val accountId = "acc_456"
        val sessionId = "session_123"
        val expectedUser = UserInfo(
            name = "Ahmed",
            username = "ahmed@example.com",
            image = "https://image.url/ahmed.jpg"
        )

        coEvery { profileRepository.getUserInfo(accountId, sessionId) } returns expectedUser

        val result = useCase(accountId, sessionId)

        assertEquals(expectedUser, result)
        coVerify(exactly = 1) { profileRepository.getUserInfo(accountId, sessionId) }
    }
}