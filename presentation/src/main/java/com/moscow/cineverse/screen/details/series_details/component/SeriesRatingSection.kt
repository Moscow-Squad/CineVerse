package com.moscow.cineverse.screen.details.series_details.component

import androidx.compose.runtime.Composable
import com.moscow.cineverse.screen.details.common.RatingBottomSheet
import com.moscow.cineverse.screen.details.series_details.SeriesDetailsScreenState

@Composable
fun SeriesRatingSection(
    uiState: SeriesDetailsScreenState,
    onRatingSubmit: (Int) -> Unit,
    onDeleteRatingSeries: () -> Unit,
    onDismissOrCancelRatingBottomSheet: () -> Unit
) {
    RatingBottomSheet(
        isVisible = uiState.showRatingBottomSheet,
        onDismiss = { onDismissOrCancelRatingBottomSheet() },
        onRatingSubmit = { onRatingSubmit(it) },
        onRatingRemove = { onDeleteRatingSeries() },
        initialRating = uiState.starsRating,
        hasExistingRating = uiState.starsRating != 0,
        isLoading = uiState.isLoading
    )
}