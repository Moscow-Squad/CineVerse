package com.moscow.cineverse.screen.details.movie_details

import com.moscow.cineverse.common_ui_state.CastUiState
import com.moscow.cineverse.common_ui_state.DurationUiState
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.ReviewUiState
import kotlinx.datetime.LocalDate


data class MovieScreenState(
    val movieDetailsUiState: MovieDetailsUiState? = null,
    val reviewsFlow: List<ReviewUiState>? = null,
    val starCast:List<CastUiState>? = null,
    val characters: List<String> = emptyList(),
    val director:List<String> = emptyList(),
    val produce: List<String> = emptyList(),
    val writer: List<String> = emptyList(),
    val recommendations:List<MediaItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isReviewEmpty: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: Int = 0,
    val recentlyViewedCollectionId:Int = 0,
    val showRatingBottomSheet: Boolean = false,
    val showLoginBottomSheet: Boolean = false,
    val starsRating: Int = 0,
    val enableBlur: String = "high"

    ) {
    data class MovieDetailsUiState(
        val id: Int,
        val title: String,
        val trailerUrl: String,
        val posterUrl: String,
        val rating: Double,
        val genres: List<String>,
        val releaseDate: LocalDate?,
        val duration: DurationUiState,
        val description: String
    )
}