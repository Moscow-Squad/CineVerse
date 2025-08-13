package com.moscow.domain.usecase.collection

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCurrentUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        getCurrentUserUseCase = GetCurrentUserUseCase(userRepository)
    }

    @Test
    fun `getCurrentUserUseCase should call repository method`() = runTest {
        // When
        getCurrentUserUseCase()

        // Then
        coVerify(exactly = 1) { userRepository.isLoggedIn() }
    }

    @Test
    fun `getCurrentUserUseCase should return result from repository`() = runTest {
        // Given
        coEvery { userRepository.isLoggedIn() } returns true

        // When
        val result = getCurrentUserUseCase()

        // Then
        assertThat(result).isTrue()
        coVerify(exactly = 1) { userRepository.isLoggedIn() }
    }

    @Test
    fun `getCurrentUserUseCase should handle multiple invocations`() = runTest {
        // Given
        coEvery { userRepository.isLoggedIn() } returns true

        // When
        repeat(3) { getCurrentUserUseCase() }

        // Then
        coVerify(exactly = 3) { userRepository.isLoggedIn() }
    }
}