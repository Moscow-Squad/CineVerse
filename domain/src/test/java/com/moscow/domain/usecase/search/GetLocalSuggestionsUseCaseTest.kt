package com.moscow.domain.usecase.search

import com.moscow.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocalSuggestionsUseCaseTest {

    private val searchRepository: SearchRepository = mockk()
    private lateinit var useCase: GetLocalSuggestionsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetLocalSuggestionsUseCase(searchRepository)
    }

    @Test
    fun `should return local suggestions`() = runTest {
        val suggestions = listOf("Inception", "Interstellar")
        coEvery { searchRepository.getLocalSuggestions() } returns flowOf(suggestions)

        val result = useCase().first()

        assertEquals(suggestions, result)
        coVerify { searchRepository.getLocalSuggestions() }
    }
}