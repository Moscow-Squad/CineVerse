package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Actor
import com.moscow.domain.repository.ActorRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.datetime.LocalDate

class GetActorDetailsUseCaseTest {

    private lateinit var actorRepository: ActorRepository
    private lateinit var getActorDetailsUseCase: GetActorDetailsUseCase

    @BeforeEach
    fun setUp() {
        actorRepository = mockk(relaxed = true)
        getActorDetailsUseCase = GetActorDetailsUseCase(actorRepository)
    }

    private val sampleActor = Actor(
        id = 1,
        name = "Tom Hanks",
        gender = Actor.Gender.MALE,
        birthDate = LocalDate(1956, 7, 9),
        placeOfBirth = "Concord, California, USA",
        biography = "Thomas Jeffrey Hanks is an American actor and filmmaker.",
        profileImg = "https://example.com/tom_hanks.jpg",
        socialMediaLinks = Actor.SocialMediaLinks(
            youtube = "https://youtube.com/tomhanks",
            facebook = "https://facebook.com/tomhanks",
            instagram = "https://instagram.com/tomhanks"
        )
    )

    private val minimalActor = Actor(
        id = 2,
        name = "Jane Doe",
        gender = Actor.Gender.FEMALE,
        birthDate = null,
        placeOfBirth = "",
        biography = "",
        profileImg = "",
        socialMediaLinks = Actor.SocialMediaLinks(
            youtube = null,
            facebook = null,
            instagram = null
        )
    )

    @Test
    fun `getActorDetailsUseCase should return actor details`() = runTest {
        // Given
        val actorId = 1
        coEvery { actorRepository.getActorDetails(actorId) } returns sampleActor

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result).isEqualTo(sampleActor)
    }

    // Edge cases
    @Test
    fun `getActorDetailsUseCase returns actor with minimal fields when data is sparse`() = runTest {
        // Given
        val actorId = 2
        coEvery { actorRepository.getActorDetails(actorId) } returns minimalActor

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.biography).isEmpty()
    }

    @Test
    fun `getActorDetailsUseCase returns female actor correctly`() = runTest {
        // Given
        val actorId = 3
        val femaleActor = sampleActor.copy(
            name = "Meryl Streep",
            gender = Actor.Gender.FEMALE
        )
        coEvery { actorRepository.getActorDetails(actorId) } returns femaleActor

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.gender).isEqualTo(Actor.Gender.FEMALE)
    }

    @Test
    fun `getActorDetailsUseCase returns actor with birth date but no place of birth`() = runTest {
        // Given
        val actorId = 4
        val actorWithBirthDateOnly = minimalActor.copy(
            birthDate = LocalDate(1985, 10, 15)
        )
        coEvery { actorRepository.getActorDetails(actorId) } returns actorWithBirthDateOnly

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.birthDate).isNotNull()
    }

    @Test
    fun `getActorDetailsUseCase returns actor with partial social media links`() = runTest {
        // Given
        val actorId = 5
        val actorWithPartialSocial = minimalActor.copy(
            socialMediaLinks = Actor.SocialMediaLinks(
                youtube = "https://youtube.com/actor",
                facebook = null,
                instagram = null
            )
        )
        coEvery { actorRepository.getActorDetails(actorId) } returns actorWithPartialSocial

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.socialMediaLinks.youtube).isNotNull()
    }

    @Test
    fun `getActorDetailsUseCase returns actor with profile image but no social media`() = runTest {
        // Given
        val actorId = 6
        val actorWithImageOnly = minimalActor.copy(
            profileImg = "https://example.com/profile.jpg"
        )
        coEvery { actorRepository.getActorDetails(actorId) } returns actorWithImageOnly

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.profileImg).isNotEmpty()
    }

    @Test
    fun `getActorDetailsUseCase returns actor with complete social media links`() = runTest {
        // Given
        val actorId = 7
        val actorWithFullSocial = sampleActor.copy(
            socialMediaLinks = Actor.SocialMediaLinks(
                youtube = "https://youtube.com/complete",
                facebook = "https://facebook.com/complete",
                instagram = "https://instagram.com/complete"
            )
        )
        coEvery { actorRepository.getActorDetails(actorId) } returns actorWithFullSocial

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.socialMediaLinks.facebook).isNotNull()
    }

    @Test
    fun `getActorDetailsUseCase returns actor with biography but no birth info`() = runTest {
        // Given
        val actorId = 8
        val actorWithBiographyOnly = minimalActor.copy(
            biography = "A talented actor with many acclaimed performances."
        )
        coEvery { actorRepository.getActorDetails(actorId) } returns actorWithBiographyOnly

        // When
        val result = getActorDetailsUseCase(actorId)

        // Then
        assertThat(result.biography).isNotEmpty()
    }

    @Test
    fun `getActorDetailsUseCase makes exactly one repository call`() = runTest {
        // Given
        val actorId = 9
        coEvery { actorRepository.getActorDetails(actorId) } returns sampleActor

        // When
        getActorDetailsUseCase(actorId)

        // Then
        coVerify(exactly = 1) { actorRepository.getActorDetails(actorId) }
    }
}