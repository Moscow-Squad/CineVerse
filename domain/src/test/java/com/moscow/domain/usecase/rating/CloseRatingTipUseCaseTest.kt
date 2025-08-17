package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.RatingTipsRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CloseRatingTipUseCaseTest {

    private val ratingTipsRepository: RatingTipsRepository = mockk()
    private lateinit var useCase: CloseRatingTipUseCase

    @BeforeEach
    fun setup() {
        useCase = CloseRatingTipUseCase(ratingTipsRepository)
    }

    @Test
    fun `invoke should call closeRatingTip`() = runTest {
        coEvery { ratingTipsRepository.closeRatingTip() } just Runs

        useCase()

        coVerify(exactly = 1) { ratingTipsRepository.closeRatingTip() }
    }
}