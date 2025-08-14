package com.moscow.domain.usecase.actor

import com.moscow.domain.model.ActorDetails
import com.moscow.domain.repository.ActorRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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
        // Given
        val actorId = 42
        val expectedDetails = ActorDetails(
            id = actorId,
            name = "John Doe",
            birthDate = LocalDate(1990, 5, 15),
            placeOfBirth = "Cairo, Egypt",
            youtubeLink = "https://youtube.com/johndoe",
            facebookLink = "https://facebook.com/johndoe",
            instagramLink = "https://instagram.com/johndoe",
            twitterLink = "https://twitter.com/johndoe",
            tiktokLink = "https://tiktok.com/johndoe",
            biography = "An accomplished actor known for dynamic roles.",
            profileImg = "https://image.url/johndoe.jpg"
        )

        coEvery { actorRepository.getActorDetails(actorId) } returns expectedDetails

        // When
        val result = useCase(actorId)

        // Then
        assertEquals(expectedDetails, result)
    }
}

