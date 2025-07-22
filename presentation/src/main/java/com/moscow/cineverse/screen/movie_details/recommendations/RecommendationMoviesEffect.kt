package com.moscow.cineverse.screen.movie_details.recommendations

sealed class RecommendationMoviesEffect {
    data object NavigateBack : RecommendationMoviesEffect()
}