package com.moscow.domain.usecase.login

import com.moscow.domain.repository.auth.LoginRepository
import io.mockk.coEvery
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
        val loginData = LoginData(username = "ahmed", password = "securePass123")
        val expectedResult = true

        coEvery { loginRepository.loginWithUsernameAndPassword(loginData) } returns expectedResult

        // When
        val result = useCase(loginData)

        // Then
        assertEquals(expectedResult, result)
    }
}
