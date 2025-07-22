package com.moscow.cineverse.screen.movie_details

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import kotlinx.datetime.LocalDate


data class MovieScreenState(

    val movieDetailsUi: MovieDetailsUi? = null,
    val reviewsFlow: List<ReviewUi>? = null,
    val starCast:List<StarCastUi>? = null,
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

    val showRatingBottomSheet: Boolean = false,
    val starsRating: Int = 0

    ) {

    data class MovieDetailsUi(
        val id: Int ,
        val title: String ,
        val posterPath: String,
        val rating: Double,
        val genres: List<String>,
        val releaseDate: LocalDate,
        val duration: Int,
        val description: String
    )

    data class StarCastUi(
        val id:Int,
        val originalName:String,
        val characterName:String,
        val profileImage:String
    )

    data class CrewUi(
        val id:Int,
        val name:String,
        val job:String
    )

    data class ReviewUi(
        val id: String,
        val name:String,
        val username:String,
        val rate:Int,
        val reviewContent:String,
        val date: String,
        val userImage:String
    )
}