package com.moscow.domain.usecase.preference

import com.moscow.domain.model.UserType.AuthenticatedUser
import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserDetailsUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetUserDetailsUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        useCase = GetUserDetailsUseCase(userRepository)
    }

    @Test
    fun `invoke should return user details`() = runTest {
        val expectedUser = AuthenticatedUser(
            id = "123",
            name = "Ahmed",
            username = "AhmedHosny",
            sessionId = "session123",
            image = "image.jpg",
            recentlyCollectionId = 456
        )

        coEvery { userRepository.getUser() } returns expectedUser

        val result = useCase()

        assertEquals(expectedUser, result)
        coVerify(exactly = 1) { userRepository.getUser() }
    }
}