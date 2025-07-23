package com.android.domain.usecase.seriesdetails

import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import com.android.domain.repository.DetailsRepository

class GetLatestSeasonsUseCase(
    private val detailsRepository: DetailsRepository,

    ) {
    suspend operator fun invoke(): List<Season> = detailsRepository.getLatestSeasons()
}