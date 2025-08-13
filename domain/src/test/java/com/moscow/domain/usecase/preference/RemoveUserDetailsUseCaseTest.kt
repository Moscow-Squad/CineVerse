package com.moscow.domain.usecase.preference

import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveUserDetailsUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var removeUserDetailsUseCase: RemoveUserDetailsUseCase

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        removeUserDetailsUseCase = RemoveUserDetailsUseCase(userRepository)
    }

    @Test
    fun `removeUserDetailsUseCase should call repository method`() = runTest {
        // When
        removeUserDetailsUseCase()

        // Then
        coVerify(exactly = 1) { userRepository.clearUser() }
    }

    @Test
    fun `removeUserDetailsUseCase should complete operation successfully`() = runTest {
        // Given
        coEvery { userRepository.clearUser() } returns Unit

        // When
        val result = removeUserDetailsUseCase()

        // Then
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { userRepository.clearUser() }
    }

    @Test
    fun `removeUserDetailsUseCase should handle multiple invocations`() = runTest {
        // When
        repeat(3) { removeUserDetailsUseCase() }

        // Then
        coVerify(exactly = 3) { userRepository.clearUser() }
    }

    @Test
    fun `removeUserDetailsUseCase makes exactly one repository call`() = runTest {
        // When
        removeUserDetailsUseCase()

        // Then
        coVerify(exactly = 1) { userRepository.clearUser() }
        confirmVerified(userRepository)
    }

    @Test
    fun `removeUserDetailsUseCase respects number of calls`() = runTest {
        // When
        repeat(2) { removeUserDetailsUseCase() }

        // Then
        coVerify(exactly = 2) { userRepository.clearUser() }
    }
}