package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddMediaItemToCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var useCase: AddMediaItemToCollectionUseCase

    @BeforeEach
    fun setup() {
        collectionsRepository = mockk()
        useCase = AddMediaItemToCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `invoke should call addMovieToCollection with mediaItemId and collectionId`() = runTest {
        val mediaItemId = 202
        val collectionId = 101

        coEvery {
            collectionsRepository.addMovieToCollection(
                mediaItemId,
                collectionId
            )
        } returns Unit

        useCase(mediaItemId, collectionId)

        coVerify(exactly = 1) {
            collectionsRepository.addMovieToCollection(
                mediaItemId,
                collectionId
            )
        }
    }
}