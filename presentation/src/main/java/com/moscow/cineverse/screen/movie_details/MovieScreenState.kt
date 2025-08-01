package com.moscow.cineverse.screen.movie_details

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.ReviewUiState
import com.moscow.cineverse.common_ui_state.StarCastUiState
import kotlinx.datetime.LocalDate


data class MovieScreenState(
    val movieDetailsUiState: MovieDetailsUiState? = null,
    val reviewsFlow: List<ReviewUiState>? = null,
    val starCast:List<StarCastUiState>? = null,
    val characters: List<String> = emptyList(),
    val director:List<String> = emptyList(),
    val produce: List<String> = emptyList(),
    val writer: List<String> = emptyList(),
    val recommendations:List<MediaItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isReviewEmpty: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",
    val recentlyViewedCollectionId:Int = 0,

    val showRatingBottomSheet: Boolean = false,
    val starsRating: Int = 0

    ) {
    data class MovieDetailsUiState(
        val id: Int ,
        val title: String ,
        val trailerPath: String,
        val posterPath: String,
        val rating: Double,
        val genres: List<String>,
        val releaseDate: LocalDate?,
        val duration: Int,
        val description: String
    )
}