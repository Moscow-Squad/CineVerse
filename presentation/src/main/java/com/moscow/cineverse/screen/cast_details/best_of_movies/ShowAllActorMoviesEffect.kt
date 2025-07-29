package com.moscow.cineverse.screen.cast_details.best_of_movies

sealed class ShowAllActorMoviesEffect {
    data class NavigateMovieDetails(val movieId: Int) : ShowAllActorMoviesEffect()
}