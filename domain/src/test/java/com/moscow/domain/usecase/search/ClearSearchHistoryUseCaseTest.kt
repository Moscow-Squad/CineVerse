package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClearSearchHistoryUseCaseTest {

    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: ClearSearchHistoryUseCase

    @BeforeEach
    fun setup() {
        useCase = ClearSearchHistoryUseCase(searchRepository)
    }

    @Test
    fun `should clear search history`() = runTest {
        useCase()
        coVerify { searchRepository.clearSearchHistory() }
    }
}