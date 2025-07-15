package com.moscow.cineverse.screen.movie_details

import android.accessibilityservice.GestureDescription
import androidx.paging.PagingData
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.explore.SuggestItemUiState
import kotlinx.coroutines.flow.Flow

data class MovieScreenState(

    val movieDetailsUi: MovieDetailsUi? = null,
    val reviewsFlow: List<ReviewUi>? = null,
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
        val releaseDate: String,
        val duration: String,
        val description: String
    )

    data class ReviewUi(
        val id:Int,
        val name:String,
        val username:String,
        val rate:Int,
        val reviewContent:String,
        val date:String,
        val userImage:String
    )

    data class ActorUi(
        val name: String,
        val icon: String,
        val nameInMovie:String,
        val id: Int
    )
    data class GenreUi(
        val id: Int,
        val name: String
    )
}

