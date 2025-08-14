package com.moscow.domain.usecase.preference

import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveUserDetailsUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: RemoveUserDetailsUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        useCase = RemoveUserDetailsUseCase(userRepository)
    }

    @Test
    fun `invoke should clear user data`() = runTest {
        coEvery { userRepository.clearUser() } returns Unit

        useCase()

        coVerify(exactly = 1) { userRepository.clearUser() }
    }
}