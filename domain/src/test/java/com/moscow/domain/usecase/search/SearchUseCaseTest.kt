package com.moscow.domain.usecase.search

import com.moscow.domain.model.Actor
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUseCaseTest {

    private val searchRepository: SearchRepository = mockk()
    private lateinit var useCase: SearchUseCase

    @BeforeEach
    fun setup() {
        useCase = SearchUseCase(searchRepository)
    }

    @Test
    fun `should search for movies`() = runTest {
        val query = "Inception"
        val page = 1
        val expected = flowOf(listOf(mockk<Movie>()))

        coEvery { searchRepository.searchMovie(query, page) } returns expected

        val result = useCase.searchMovie(query, page)

        assertEquals(expected, result)
        coVerify { searchRepository.searchMovie(query, page) }
    }

    @Test
    fun `should search for series`() = runTest {
        val query = "Breaking Bad"
        val page = 1
        val expected = flowOf(listOf(mockk<Series>()))

        coEvery { searchRepository.searchSeries(query, page) } returns expected

        val result = useCase.searchSeries(query, page)

        assertEquals(expected, result)
        coVerify { searchRepository.searchSeries(query, page) }
    }

    @Test
    fun `should search for actors`() = runTest {
        val query = "Leonardo DiCaprio"
        val page = 1
        val expected = flowOf(listOf(mockk<Actor>()))

        coEvery { searchRepository.searchActor(query, page) } returns expected

        val result = useCase.searchActor(query, page)

        assertEquals(expected, result)
        coVerify { searchRepository.searchActor(query, page) }
    }
}