package com.moscow.domain.usecase.preference

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.domain.model.UserType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUserDetailsUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        getUserDetailsUseCase = GetUserDetailsUseCase(userRepository)
    }

    @Test
    fun `getUserDetailsUseCase should call repository method`() = runTest {
        // When
        getUserDetailsUseCase()

        // Then
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `getUserDetailsUseCase should return result from repository`() = runTest {
        // Given
        val expected = UserType.AuthenticatedUser(id = "42", name = "testUser", username = "testUsername", sessionId = "testSessionId", image = "testImage", recentlyCollectionId = 1)
        coEvery { userRepository.getUser() } returns expected

        // When
        val result = getUserDetailsUseCase()

        // Then
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `getUserDetailsUseCase should complete operation successfully`() = runTest {
        // Given
        coEvery { userRepository.getUser() } returns UserType.GuestUser(sessionId = "testSessionId", expiredAt = "testExpiredAt")

        // When
        val result = getUserDetailsUseCase()

        // Then
        assertThat(result).isNotNull()
        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `getUserDetailsUseCase should handle multiple invocations`() = runTest {
        // When
        repeat(3) { getUserDetailsUseCase() }

        // Then
        coVerify(exactly = 3) { userRepository.getUser() }
    }

    @Test
    fun `getUserDetailsUseCase makes exactly one repository call`() = runTest {
        // When
        getUserDetailsUseCase()

        // Then
        coVerify(exactly = 1) { userRepository.getUser() }
        confirmVerified(userRepository)
    }

    @Test
    fun `getUserDetailsUseCase respects number of calls`() = runTest {
        // When
        repeat(2) { getUserDetailsUseCase() }

        // Then
        coVerify(exactly = 2) { userRepository.getUser() }
    }
}