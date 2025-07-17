package com.moscow.cineverse.screen.series_details

import com.android.domain.usecase.GetLatestSeasonsUseCase
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.GetSeriesDetailUseCase
import com.moscow.cineverse.base.BaseViewModel

class SeriesDetailsViewModel(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase,
    private val getLatestSeasonsUseCase: GetLatestSeasonsUseCase,
) : BaseViewModel<SeriesDetailsUiState, Unit>(SeriesDetailsUiState()) {

    fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getSeriesDetailUseCase(seriesId) },
            onSuccess = { detail ->
                updateState { it.copy(seriesDetail = detail, isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )
    }

    fun loadReviews(seriesId: Int, page: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getReviewsPageUseCase(seriesId, page, isMovie = false) },
            onSuccess = { reviews ->
                updateState { it.copy(reviews = reviews, isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )
    }

    fun latestSeasons() {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getLatestSeasonsUseCase() },
            onSuccess = { _ ->
                updateState { it.copy(isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )

    }
}