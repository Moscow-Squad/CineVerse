package com.moscow.cineverse.screen.series_details


import com.android.domain.model.SeriesDetail
import com.android.domain.model.Review

data class SeriesDetailsUiState(
    val isLoading: Boolean = false,
    val seriesDetail: SeriesDetail? = null,
    val reviews: List<Review> = emptyList(),
    val error: String? = null
)