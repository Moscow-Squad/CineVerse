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
class AddNewCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var useCase: AddNewCollectionUseCase

    @BeforeEach
    fun setup() {
        collectionsRepository = mockk()
        useCase = AddNewCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `invoke should call addNewCollection with name and description`() = runTest {
        val name = "Favorites"
        val description = "My favorite movies"

        coEvery { collectionsRepository.addNewCollection(name, description) } returns 1

        useCase(name, description)

        coVerify(exactly = 1) { collectionsRepository.addNewCollection(name, description) }
    }
}