package com.moscow.domain.usecase.login

import com.moscow.domain.repository.auth.LoginRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LoginWithUsernameAndPasswordUseCaseTest {

    private lateinit var loginRepository: LoginRepository
    private lateinit var useCase: LoginWithUsernameAndPasswordUseCase

    @BeforeEach
    fun setup() {
        loginRepository = mockk()
        useCase = LoginWithUsernameAndPasswordUseCase(loginRepository)
    }

    @Test
    fun `invoke should return login result`() = runTest {
        // Given
        val username = "ahmed"
        val password = "securePass123"
        val expectedResult = true
        coEvery {
            loginRepository.loginWithUsernameAndPassword(username, password)
        } returns expectedResult

        // When
        val result = useCase(username, password)

        // Then
        assertEquals(expectedResult, result)
        coVerify(exactly = 1) {
            loginRepository.loginWithUsernameAndPassword(username, password)
        }
    }
}