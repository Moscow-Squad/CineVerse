package com.moscow.domain.usecase.genre

import com.moscow.domain.model.Genre
import com.moscow.domain.repository.GenreRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GenreUseCaseTest {

    private lateinit var genreRepository: GenreRepository
    private lateinit var useCase: GenreUseCase

    @BeforeEach
    fun setup() {
        genreRepository = mockk()
        useCase = GenreUseCase(genreRepository)
    }

    @Test
    fun `getMoviesGenres should return movie genres`() = runTest {
        val expectedGenres = listOf(
            Genre(
                id = 1,
                name = "Action"
            ),
            Genre(
                id = 2,
                name = "Drama"
            ),
        )
        coEvery { genreRepository.getMoviesGenres() } returns expectedGenres

        val result = useCase.getMoviesGenres()

        assertEquals(expectedGenres, result)
    }

    @Test
    fun `getSeriesGenres should return series genres`() = runTest {
        val expectedGenres = listOf(
            Genre(
                id = 1,
                name = "Thriller"
            ),
            Genre(
                id = 2,
                name = "Documentary"
            ),
        )
        coEvery { genreRepository.getSeriesGenres() } returns expectedGenres

        val result = useCase.getSeriesGenres(false)

        assertEquals(expectedGenres, result)
    }
}