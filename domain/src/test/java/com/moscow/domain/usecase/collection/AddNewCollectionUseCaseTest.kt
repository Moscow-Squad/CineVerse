package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddNewCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var addNewCollectionUseCase: AddNewCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        addNewCollectionUseCase = AddNewCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `addNewCollectionUseCase should create new collection with name and description`() = runTest {
        // Given
        val collectionName = "My Favorite Movies"
        val collectionDescription = "A collection of my all-time favorite films"

        // When
        val result = addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addNewCollectionUseCase should create collection with name only`() = runTest {
        // Given
        val collectionName = "Action Movies"
        val collectionDescription = null

        // When
        val result = addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addNewCollectionUseCase should handle empty description`() = runTest {
        // Given
        val collectionName = "Comedy Collection"
        val collectionDescription = ""

        // When
        addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.addNewCollection(collectionName, collectionDescription)
        }
    }

    @Test
    fun `addNewCollectionUseCase should handle long collection name`() = runTest {
        // Given
        val collectionName = "My Super Long Collection Name That Contains Many Words And Characters"
        val collectionDescription = "A detailed description"

        // When
        val result = addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addNewCollectionUseCase should handle short collection name`() = runTest {
        // Given
        val collectionName = "Top 10"
        val collectionDescription = "Best movies"

        // When
        val result = addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addNewCollectionUseCase should pass correct parameters to repository`() = runTest {
        // Given
        val collectionName = "Horror Movies"
        val collectionDescription = "Scary films for Halloween"

        // When
        addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        coVerify(exactly = 1) {
            collectionsRepository.addNewCollection(
                collectionName = collectionName,
                collectionDescription = collectionDescription
            )
        }
    }

    @Test
    fun `addNewCollectionUseCase should handle collection with special characters`() = runTest {
        // Given
        val collectionName = "90's Movies & TV Shows"
        val collectionDescription = "Movies from the 1990's era!"

        // When
        val result = addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        assertThat(result).isNotNull()
    }

    @Test
    fun `addNewCollectionUseCase should create collection with null description successfully`() = runTest {
        // Given
        val collectionName = "Documentaries"
        val collectionDescription: String? = null

        // When
        addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        coVerify {
            collectionsRepository.addNewCollection(collectionName, null)
        }
    }

    @Test
    fun `addNewCollectionUseCase makes exactly one repository call`() = runTest {
        // Given
        val collectionName = "Sci-Fi Collection"
        val collectionDescription = "Science fiction movies"

        // When
        addNewCollectionUseCase(collectionName, collectionDescription)

        // Then
        coVerify(exactly = 1) { collectionsRepository.addNewCollection(any(), any()) }
    }
}