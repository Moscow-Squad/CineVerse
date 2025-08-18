package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SuggestionUseCaseTest {

    private val searchRepository: SearchRepository = mockk()
    private lateinit var useCase: SuggestionUseCase

    @BeforeEach
    fun setup() {
        useCase = SuggestionUseCase(searchRepository)
    }

    @Test
    fun `should return remote suggestions`() = runTest {
        val keyword = "Incep"
        val page = 1
        val expected = listOf("Inception", "Inception 2")

        coEvery { searchRepository.getRemoteSuggestions(keyword, page) } returns expected

        val result = useCase(keyword, page)

        assertEquals(expected, result)
        coVerify { searchRepository.getRemoteSuggestions(keyword, page) }
    }
}