package com.moscow.cineverse.details.movie_details.recommendations

sealed class RecommendationMoviesEffect {
    data object NavigateBack : RecommendationMoviesEffect()
    data class MovieClicked(val movieId: Int) : RecommendationMoviesEffect()
}