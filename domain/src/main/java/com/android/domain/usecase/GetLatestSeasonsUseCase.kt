package com.android.domain.usecase

import com.android.domain.model.SeriesDetail
import com.android.domain.repository.DetailsRepository

class GetLatestSeasonsUseCase(
    private val detailsRepository: DetailsRepository,

    ) {
    suspend operator fun invoke(): List<SeriesDetail> = detailsRepository.getLatestSeasons()
}