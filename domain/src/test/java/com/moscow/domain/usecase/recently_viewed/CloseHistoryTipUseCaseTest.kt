package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.repository.HistoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CloseHistoryTipUseCaseTest {

    private lateinit var historyTipsRepository: HistoryTipsRepository
    private lateinit var useCase: CloseHistoryTipUseCase

    @BeforeEach
    fun setup() {
        historyTipsRepository = mockk()
        useCase = CloseHistoryTipUseCase(historyTipsRepository)
    }

    @Test
    fun `invoke should call closeHistoryTip`() = runTest {
        coEvery { historyTipsRepository.closeHistoryTip() } returns Unit

        useCase()

        coVerify(exactly = 1) { historyTipsRepository.closeHistoryTip() }
    }
}