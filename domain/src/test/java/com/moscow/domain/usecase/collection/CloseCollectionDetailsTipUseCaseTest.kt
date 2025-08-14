package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CategoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CloseCollectionDetailsTipUseCaseTest {

    private lateinit var categoryTipsRepository: CategoryTipsRepository
    private lateinit var useCase: CloseCollectionDetailsTipUseCase

    @BeforeEach
    fun setup() {
        categoryTipsRepository = mockk()
        useCase = CloseCollectionDetailsTipUseCase(categoryTipsRepository)
    }

    @Test
    fun `invoke should call closeCategoryDetailsTip`() = runTest {
        coEvery { categoryTipsRepository.closeCategoryDetailsTip() } returns Unit

        useCase()

        coVerify(exactly = 1) { categoryTipsRepository.closeCategoryDetailsTip() }
    }
}