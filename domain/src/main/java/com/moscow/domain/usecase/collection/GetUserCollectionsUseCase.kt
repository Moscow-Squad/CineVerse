package com.moscow.domain.usecase.collection

import com.moscow.domain.repository.CollectionsRepository
import javax.inject.Inject

class GetUserCollectionsUseCase @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) {
    suspend operator fun invoke(page: Int) = collectionsRepository.getCollections(page)
}