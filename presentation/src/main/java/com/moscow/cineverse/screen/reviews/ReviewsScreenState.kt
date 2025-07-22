package com.moscow.cineverse.screen.reviews

data class ReviewsScreenState(
    val reviewsFlow: List<ReviewUiState>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isReviewEmpty: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = ""
    )

    data class ReviewUiState(
        val id: String,
        val name:String,
        val username:String,
        val rate:Int,
        val reviewContent:String,
        val date:String,
        val userImage:String
    )



