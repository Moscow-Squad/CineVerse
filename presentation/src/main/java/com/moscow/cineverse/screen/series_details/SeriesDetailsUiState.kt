package com.moscow.cineverse.screen.series_details


import com.android.domain.model.Review
import com.android.domain.model.details.SeriesDetail

data class SeriesDetailsUiState(
    val isLoading: Boolean = false,
    val seriesDetail: SeriesDetail? = null,
    val reviews: List<Review> = emptyList(),
    val error: String? = null,
    val latestSeason: List<SeriesDetail> = emptyList(),
    val listOfSeries: List<SeriesDetail> = emptyList(),
)