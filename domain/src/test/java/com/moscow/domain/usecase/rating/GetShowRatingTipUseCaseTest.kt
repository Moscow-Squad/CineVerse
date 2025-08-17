package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.RatingTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetShowRatingTipUseCaseTest {

    private val ratingTipsRepository: RatingTipsRepository = mockk()
    private lateinit var useCase: GetShowRatingTipUseCase

    @BeforeEach
    fun setup() {
        useCase = GetShowRatingTipUseCase(ratingTipsRepository)
    }

    @Test
    fun `invoke should call showRatingTip`() = runTest {

        coEvery { ratingTipsRepository.showRatingTip() } returns true

        useCase()

        coVerify { ratingTipsRepository.showRatingTip() }
    }
}