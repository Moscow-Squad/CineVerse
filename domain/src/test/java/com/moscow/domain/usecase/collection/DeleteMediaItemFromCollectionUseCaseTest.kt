package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteMediaItemFromCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var useCase: DeleteMediaItemFromCollectionUseCase

    @BeforeEach
    fun setup() {
        collectionsRepository = mockk()
        useCase = DeleteMediaItemFromCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `invoke should call deleteMovieFromCollection with mediaItemId and collectionId`() =
        runTest {
            val mediaItemId = 202
            val collectionId = 101

            coEvery {
                collectionsRepository.deleteMovieFromCollection(
                    mediaItemId,
                    collectionId
                )
            } returns Unit

            useCase(mediaItemId, collectionId)

            coVerify(exactly = 1) {
                collectionsRepository.deleteMovieFromCollection(
                    mediaItemId,
                    collectionId
                )
            }
        }
}