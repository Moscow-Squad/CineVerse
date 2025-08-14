package com.moscow.domain.usecase.actor

import com.moscow.domain.repository.ActorRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetActorGalleryUseCaseTest {

    private lateinit var actorRepository: ActorRepository
    private lateinit var useCase: GetActorGalleryUseCase

    @BeforeEach
    fun setup() {
        actorRepository = mockk()
        useCase = GetActorGalleryUseCase(actorRepository)
    }

    @Test
    fun `invoke should return actor gallery`() = runTest {
        // Given
        val actorId = 101
        val expectedGallery = listOf(
            "https://image.url/actor1.jpg",
            "https://image.url/actor2.jpg",
            "https://image.url/actor3.jpg"
        )
        coEvery { actorRepository.getActorGalleryUrl(actorId) } returns expectedGallery

        // When
        val result = useCase(actorId)

        // Then
        assertEquals(expectedGallery, result)
    }
}
