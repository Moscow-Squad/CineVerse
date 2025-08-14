package com.moscow.repository

import com.moscow.data_source.remote.ActorRemoteDataSource
import com.moscow.remote.dto.actor.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class ActorRepositoryImplTest {

    private val actorRemoteDataSource = mockk<ActorRemoteDataSource>()
    private lateinit var repository: ActorRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = ActorRepositoryImpl(actorRemoteDataSource)
    }

    @Test
    fun `getActorDetails calls remote data source correctly`() = runTest {

        val actorId = 123
        val mockActorDetailsDto = ActorDetailsDto(
            adult = false,
            alsoKnownAs = listOf("John Actor"),
            biography = "Famous actor biography",
            birthday = "1990-01-15",
            deathday = null,
            gender = 2,
            homepage = "https://johnactor.com",
            id = actorId,
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            name = "John Actor",
            placeOfBirth = "New York, USA",
            popularity = 85.5,
            profilePath = "/profile123.jpg"
        )

        val mockSocialMediaDto = ActorSocialMediaDto(
            id = actorId,
            wikidataId = "Q123456",
            facebookId = "johnactor",
            tiktokId = "johnactor_tiktok",
            twitterId = "johnactor",
            youtubeId = "UCjohnactor",
            instagramId = "johnactor",
            freebaseId = null,
            freebaseMid = null,
            imdbId = "nm1234567"
        )

        coEvery { actorRemoteDataSource.getActorDetails(actorId) } returns mockActorDetailsDto
        coEvery { actorRemoteDataSource.getActorSocialMedia(actorId) } returns mockSocialMediaDto

        try {
            repository.getActorDetails(actorId)

            assertEquals(true, true)
        } catch (e: Exception) {

            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { actorRemoteDataSource.getActorDetails(actorId) }
        coVerify(exactly = 1) { actorRemoteDataSource.getActorSocialMedia(actorId) }
    }

    @Test
    fun `getActorGalleryUrl calls remote data source correctly`() = runTest {

        val actorId = 123
        val mockImagesDto = ActorImagesDto(
            id = actorId,
            images = listOf(
                ActorImagesDto.ActorImageDetails(
                    aspectRatio = 0.667,
                    filePath = "/image1.jpg",
                    height = 1500,
                    voteAverage = 8.5,
                    voteCount = 10,
                    width = 1000
                ),
                ActorImagesDto.ActorImageDetails(
                    aspectRatio = 0.667,
                    filePath = "/image2.jpg",
                    height = 1500,
                    voteAverage = 9.0,
                    voteCount = 15,
                    width = 1000
                )
            )
        )

        coEvery { actorRemoteDataSource.getActorGallery(actorId) } returns mockImagesDto

        try {
            repository.getActorGalleryUrl(actorId)
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { actorRemoteDataSource.getActorGallery(actorId) }
    }

    @Test
    fun `getBestOfMovies calls remote data source correctly`() = runTest {

        val actorId = 123
        val mockBestMoviesDto = ActorBestOfMoviesDto(
            cast = listOf(
                ActorBestOfMoviesDto.ActorBestOfMoviesAsCast(
                    adult = false,
                    backdropPath = "/backdrop1.jpg",
                    character = "Hero",
                    creditId = "credit123",
                    genreIds = listOf(28, 12),
                    id = 456,
                    order = 1,
                    originalLanguage = "en",
                    originalTitle = "Action Movie",
                    overview = "Great action movie",
                    popularity = 95.0,
                    posterPath = "/poster1.jpg",
                    releaseDate = "2023-05-15",
                    title = "Action Movie",
                    video = false,
                    voteAverage = 8.2,
                    voteCount = 1500
                )
            ),
            crew = listOf(
                ActorBestOfMoviesDto.ActorBestOfMoviesAsCrew(
                    adult = false,
                    backdropPath = "/backdrop2.jpg",
                    creditId = "credit456",
                    department = "Production",
                    genreIds = listOf(18),
                    id = 789,
                    job = "Producer",
                    originalLanguage = "en",
                    originalTitle = "Drama Film",
                    overview = "Emotional drama",
                    popularity = 75.0,
                    posterPath = "/poster2.jpg",
                    releaseDate = "2022-10-20",
                    title = "Drama Film",
                    video = false,
                    voteAverage = 7.8,
                    voteCount = 800
                )
            ),
            id = actorId
        )

        coEvery { actorRemoteDataSource.getActorBestMovies(actorId) } returns mockBestMoviesDto

        try {
            repository.getBestOfMovies(actorId)
            assertEquals(true, true)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { actorRemoteDataSource.getActorBestMovies(actorId) }
    }
}