package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetCurrentUserUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        useCase = GetCurrentUserUseCase(userRepository)
    }

    @Test
    fun `invoke should call isLoggedIn`() = runTest {
        val expectedResult = true

        coEvery { userRepository.isLoggedIn() } returns expectedResult

        val result = useCase()

        assertEquals(expectedResult, result)
        coVerify(exactly = 1) { userRepository.isLoggedIn() }
    }
}