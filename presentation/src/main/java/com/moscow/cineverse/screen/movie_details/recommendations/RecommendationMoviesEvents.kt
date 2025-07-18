package com.moscow.cineverse.screen.movie_details.recommendations

import com.moscow.cineverse.screen.reviews.ReviewsScreenEvents

sealed class RecommendationMoviesEvents {

    data object NavigateBack: RecommendationMoviesEvents()
}