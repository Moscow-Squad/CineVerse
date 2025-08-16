package com.moscow.data.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.ActorRemoteDataSource
import com.moscow.remote.data_source.ActorRemoteDataSourceImpl
import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.actor.ActorSocialMediaDto
import com.moscow.remote.dto.actor.ActorDetailsDto
import com.moscow.remote.services.ActorService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class ActorRemoteDataSourceImplTest {
    private lateinit var actorService: ActorService
    private lateinit var actorRemoteDataSource: ActorRemoteDataSource

    @BeforeEach
    fun setup() {
        actorService = mockk()
        actorRemoteDataSource = ActorRemoteDataSourceImpl(actorService)
    }

    @Test
    fun `given actorId when getActorBestMovies succeeds then return actor details`() = runTest {

        val expected = ActorDetailsDto(
            id = 1,
            name = "Mohamed",
            adult = false,
            alsoKnownAs = listOf(),
            biography = "",
            birthday = "16/12/1985",
            deathday = "10/12/2012",
            gender = 0,
            homepage ="",
            imdbId = "",
            knownForDepartment = "",
            placeOfBirth = "US",
            popularity = 0.0,
            profilePath = ""
        )
        coEvery { actorService.getActorDetails(1) } returns Response.success(expected)

        val result  = actorRemoteDataSource.getActorDetails(1)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            actorService.getActorDetails(1)
        }
    }

    @Test
    fun `given actorId when getActorGallery succeeds then return gallery`() = runTest {

        val expected = ActorImagesDto(
            id = 1,
            images = listOf()
        )
        coEvery { actorService.getActorGallery(1) } returns Response.success(expected)

        val result  = actorRemoteDataSource.getActorGallery(1)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            actorService.getActorGallery(1)
        }
    }

    @Test
    fun `given actorId when getActorBestMovies succeeds then return best movies`() = runTest {

        val expected = ActorBestOfMoviesDto(
            id = 1,
            crew = listOf(),
            cast = listOf()
        )
        coEvery { actorService.getActorBestMovies(1) } returns Response.success(expected)

        val result  = actorRemoteDataSource.getActorBestMovies(1)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            actorService.getActorBestMovies(1)
        }
    }

    @Test
    fun `given actorId when getActorSocialMedia succeeds then return social media`() = runTest {

        val expected = ActorSocialMediaDto(
            id = 1,
            wikidataId = "Q123456",
            facebookId = "actor.fb",
            tiktokId = "actor.tt",
            twitterId = "actor_tw",
            youtubeId = "actorYTchannel",
            instagramId = "actor_insta",
            freebaseId = "fb123",
            freebaseMid = "m.0abcd",
            imdbId = "nm1234567"
        )
        coEvery { actorService.getActorSocialMedia(1) } returns Response.success(expected)

        val result  = actorRemoteDataSource.getActorSocialMedia(1)
        assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) {
            actorService.getActorSocialMedia(1)
        }
    }


}