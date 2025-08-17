package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.RatingTipsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CloseRatingTipUseCaseTest {

    private lateinit var ratingTipsRepository: RatingTipsRepository
    private lateinit var useCase: CloseRatingTipUseCase

    @BeforeEach
    fun setup() {
        ratingTipsRepository = mockk(relaxed = true)
        useCase = CloseRatingTipUseCase(ratingTipsRepository)
    }

    @Test
    fun `should invoke closeRatingTip on repository`() = runTest {
        useCase()
        coVerify { ratingTipsRepository.closeRatingTip() }
    }
}