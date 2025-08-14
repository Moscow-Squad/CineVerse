package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CategoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetShowCollectionDetailsTipUseCaseTest {

    private lateinit var categoryTipsRepository: CategoryTipsRepository
    private lateinit var useCase: GetShowCollectionDetailsTipUseCase

    @BeforeEach
    fun setup() {
        categoryTipsRepository = mockk()
        useCase = GetShowCollectionDetailsTipUseCase(categoryTipsRepository)
    }

    @Test
    fun `invoke should call showCategoryDetailsTip`() = runTest {
        val expectedResult = true

        coEvery { categoryTipsRepository.showCategoryDetailsTip() } returns expectedResult

        val result = useCase()

        assertEquals(expectedResult, result)
        coVerify(exactly = 1) { categoryTipsRepository.showCategoryDetailsTip() }
    }
}