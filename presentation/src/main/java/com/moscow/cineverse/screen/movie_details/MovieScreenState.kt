package com.moscow.cineverse.screen.movie_details

import com.moscow.cineverse.screen.component.movie_poster_card.MediaItemUi
import kotlinx.datetime.LocalDate


data class MovieScreenState(

    val movieDetailsUi: MovieDetailsUi? = null,
    val reviewsFlow: List<ReviewUi>? = null,
    val starCast:List<StarCastUi>? = null,
    val characters: List<String> = emptyList(),
    val director:List<String> = emptyList(),
    val produce: List<String> = emptyList(),
    val writer: List<String> = emptyList(),
    val recommendations:List<MediaItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isReviewEmpty: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",

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
        val date:String,
        val userImage:String
    )

    data class GenreUi(
        val id: Int,
        val name: String
    )

    data class MovieUi(
        val id:Int,
        val name:String,
        val poster:String
    )
}

