package com.moscow.domain.usecase.rating

import com.moscow.domain.model.UserType
import com.moscow.domain.repository.SeriesRepository
import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetRatedSeriesUseCaseTest {

    private val seriesRepository: SeriesRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var useCase: GetRatedSeriesUseCase

    @BeforeEach
    fun setup() {
        useCase = GetRatedSeriesUseCase(seriesRepository, userRepository)
    }

    @Test
    fun `invoke should parse embedded userId and return rated series`() = runTest {
        val user = UserType.AuthenticatedUser(
            id = "id=42,token=abc",
            name = "Ahmed",
            username = "Ahmed",
            sessionId = "123",
            image = null,
            recentlyCollectionId = 1
        )

        val expected = listOf(
            GetRatedSeriesUseCase.RatedSeriesResult(series = mockk(), rating = 4.0f)
        )

        coEvery { userRepository.getUser() } returns user
        coEvery { seriesRepository.getRatedSeries(42, 1) } returns expected

        val result = useCase(1)

        assertEquals(expected, result)
        coVerify { seriesRepository.getRatedSeries(42, 1) }
    }

    @Test
    fun `invoke should fallback to 0 when user is guest`() = runTest {
        val user = UserType.GuestUser(
            sessionId = "123",
            expiredAt = "2023-12-31T23:59:59Z"
        )

        coEvery { userRepository.getUser() } returns user
        coEvery { seriesRepository.getRatedSeries(0, 1) } returns emptyList()

        val result = useCase(1)

        assertEquals(0, result.size)
        coVerify { seriesRepository.getRatedSeries(0, 1) }
    }

    @Test
    fun `invoke should fallback to 0 when userId is malformed`() = runTest {
        val user = UserType.AuthenticatedUser(
            id = "not_a_number",
            name = "Ahmed",
            username = "Ahmed",
            sessionId = "123",
            image = null,
            recentlyCollectionId = 1
        )

        coEvery { userRepository.getUser() } returns user
        coEvery { seriesRepository.getRatedSeries(0, 1) } returns emptyList()

        val result = useCase(1)

        assertEquals(0, result.size)
        coVerify { seriesRepository.getRatedSeries(0, 1) }
    }
}