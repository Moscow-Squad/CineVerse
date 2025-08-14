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
class ClearCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var useCase: ClearCollectionUseCase

    @BeforeEach
    fun setup() {
        collectionsRepository = mockk()
        useCase = ClearCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `invoke should call clearCollection with collectionId`() = runTest {
        val collectionId = 101

        coEvery { collectionsRepository.clearCollection(collectionId) } returns Unit

        useCase(collectionId)

        coVerify(exactly = 1) { collectionsRepository.clearCollection(collectionId) }
    }
}