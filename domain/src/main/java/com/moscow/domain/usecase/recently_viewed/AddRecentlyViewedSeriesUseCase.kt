package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.model.Series
import com.moscow.domain.repository.RecentlyViewedRepository
import javax.inject.Inject

class AddRecentlyViewedSeriesUseCase @Inject constructor(
    private val repository: RecentlyViewedRepository
) {
    suspend operator fun invoke(
        series: Series
    ) = repository.addRecentlyViewedSeries(series = series)
}