package com.moscow.cineverse.screen.movie_details.recommendations

sealed class RecommendationMoviesEvents {

    data object NavigateBack: RecommendationMoviesEvents()
}