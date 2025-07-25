package com.moscow.domain.usecase.login

import com.moscow.domain.repository.LoginRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LoginAsGuestUseCaseTest {

    private lateinit var loginRepository: LoginRepository
    private lateinit var useCase: LoginAsGuestUseCase

    @BeforeEach
    fun setup() {
        loginRepository = mockk()
        useCase = LoginAsGuestUseCase(loginRepository)
    }

    @Test
    fun `invoke should return guest login result`() = runTest {
        // Given
        val expectedResult = true
        coEvery { loginRepository.loginAsGuest() } returns expectedResult

        // When
        val result = useCase()

        // Then
        assertEquals(expectedResult, result)
    }
}
