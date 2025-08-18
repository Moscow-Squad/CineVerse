package com.moscow.domain.usecase.series

import com.moscow.domain.model.Genre
import com.moscow.domain.model.Series
import com.moscow.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPopularSeriesUseCaseTest {

    private val repository: SeriesRepository = mockk()
    private lateinit var useCase: GetPopularSeriesUseCase

    @BeforeEach
    fun setup() {
        useCase = GetPopularSeriesUseCase(repository)
    }

    @Test
    fun `should fetch distinct popular series`() = runTest {
        val sampleSeries = Series(
            id = 1,
            title = "The Great Show",
            overview = "A thrilling drama series.",
            posterPath = "/poster.jpg",
            trailerPath = "/trailer.mp4",
            backdropPath = "/backdrop.jpg",
            genres = listOf(Genre(id = 101, name = "Drama")),
            genreIds = listOf(101),
            rating = 8.5f,
            voteCount = 1200,
            releaseDate = LocalDate(2022, 10, 5),
            type = "TV",
            creators = listOf(Series.Creator(id = 1, name = "Jane Doe", profilePath = "/jane.jpg")),
            numberOfSeasons = 3,
            numberOfEpisodes = 30,
            seasons = listOf(
                Series.Season(
                    id = 11,
                    name = "Season 1",
                    airDate = LocalDate(2022, 10, 5),
                    episodeCount = 10,
                    posterPath = "/season1.jpg",
                    overview = "The beginning.",
                    rate = 8.0f
                )
            )
        )

        val seriesList = listOf(
            sampleSeries,
            sampleSeries,
            sampleSeries.copy(id = 2),
        )
        coEvery { repository.getPopularSeries(1) } returns seriesList

        val result = useCase(1)

        assertEquals(2, result.size)
        coVerify { repository.getPopularSeries(1) }
    }
}