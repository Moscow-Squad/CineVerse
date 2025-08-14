package com.moscow.domain.usecase.collection

import com.moscow.domain.model.Collection
import com.moscow.domain.repository.CollectionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserCollectionsUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var useCase: GetUserCollectionsUseCase

    @BeforeEach
    fun setup() {
        collectionsRepository = mockk()
        useCase = GetUserCollectionsUseCase(collectionsRepository)
    }

    @Test
    fun `invoke should call getCollections with page`() = runTest {
        val page = 1
        val expectedCollections = listOf<Collection>(
            Collection(
                id = 1,
                name = "Collection 1",
                itemCount = 10
            )
        )

        coEvery { collectionsRepository.getCollections(page) } returns expectedCollections

        val result = useCase(page)

        assertEquals(expectedCollections, result)
        coVerify(exactly = 1) { collectionsRepository.getCollections(page) }
    }
}