package com.moscow.domain.usecase.login

import com.moscow.domain.repository.auth.LoginRepository
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
        val expectedResult = true
        coEvery { loginRepository.loginAsGuest() } returns expectedResult

        val result = useCase()

        assertEquals(expectedResult, result)
    }
}