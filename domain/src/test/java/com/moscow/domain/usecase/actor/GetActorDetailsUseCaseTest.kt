package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Actor
import com.moscow.domain.repository.ActorRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetActorDetailsUseCaseTest {

    private lateinit var actorRepository: ActorRepository
    private lateinit var useCase: GetActorDetailsUseCase

    @BeforeEach
    fun setup() {
        actorRepository = mockk()
        useCase = GetActorDetailsUseCase(actorRepository)
    }

    @Test
    fun `invoke should return actor details`() = runTest {
        val actorId = 42
        val expectedDetails = Actor(
            id = actorId,
            name = "John Doe",
            gender = Actor.Gender.MALE,
            birthDate = LocalDate(1990, 5, 15),
            placeOfBirth = "Cairo, Egypt",
            biography = "An accomplished actor known for dynamic roles.",
            profileImg = "https://image.url/johndoe.jpg",
            socialMediaLinks = Actor.SocialMediaLinks(
                youtube = "https://youtube.com/johndoe",
                facebook = "https://facebook.com/johndoe",
                instagram = "https://instagram.com/johndoe",
                tiktok = null,
                twitter = null
            )
        )

        coEvery { actorRepository.getActorDetails(actorId) } returns expectedDetails

        val result = useCase(actorId)

        assertEquals(expectedDetails, result)
        coVerify(exactly = 1) { actorRepository.getActorDetails(actorId) }
    }
}