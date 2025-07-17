package com.android.domain.usecase

import com.android.domain.repository.DetailsRepository

class GetRecommendationsUseCase(
    private val detailsRepository: DetailsRepository
) {
    suspend operator fun invoke(id:Int,page:Int) =
        detailsRepository.getRecommendations(id,page)
}