package com.moscow.domain.usecase.collection

import com.moscow.domain.model.Movie
import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCollectionDetailsUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var useCase: GetCollectionDetailsUseCase

    @BeforeEach
    fun setup() {
        collectionsRepository = mockk()
        useCase = GetCollectionDetailsUseCase(collectionsRepository)
    }

    @Test
    fun `invoke should call getCollectionMovies with collectionId and page`() = runTest {
        val collectionId = 101
        val page = 2
        val expectedResult = listOf<Movie>(
            Movie(
                id = 101,
                title = "Top Hit",
                overview = "A blockbuster movie loved by fans.",
                trailerUrl = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
                backdropUrl = "/backdrop/top_hit.jpg",
                posterUrl = "https://image.tmdb.org/t/p/w500/top_hit.jpg",
                rating = 8.7f,
                genreIds = listOf(1, 2),
                releaseDate = LocalDate(2020, 5, 20),
                genres = listOf("Action", "Adventure"),
                duration = Movie.Duration(hours = 2, minutes = 30)
            )
        )

        coEvery {
            collectionsRepository.getCollectionMovies(
                collectionId,
                page
            )
        } returns expectedResult

        val result = useCase(collectionId, page)

        assertEquals(expectedResult, result)
        coVerify(exactly = 1) { collectionsRepository.getCollectionMovies(collectionId, page) }
    }
}