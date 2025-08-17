package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CacheSearchQueryUseCaseTest {

    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: CacheSearchQueryUseCase

    @BeforeEach
    fun setup() {
        useCase = CacheSearchQueryUseCase(searchRepository)
    }

    @Test
    fun `should cache search query`() = runTest {
        val query = "Inception"
        useCase(query)
        coVerify { searchRepository.cacheSearchQuery(query) }
    }
}