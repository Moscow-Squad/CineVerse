package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CategoryTipsRepository
import javax.inject.Inject

class CloseCollectionDetailsTipUseCase @Inject constructor(
    private val categoryTipsRepository: CategoryTipsRepository
) {
    suspend operator fun invoke() =
        categoryTipsRepository.closeCategoryDetailsTip()
}