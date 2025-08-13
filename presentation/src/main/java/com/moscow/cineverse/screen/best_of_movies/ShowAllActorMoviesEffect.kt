package com.moscow.cineverse.screen.best_of_movies

sealed class ShowAllActorMoviesEffect {
    data class NavigateMovieDetails(val movieId: Int) : ShowAllActorMoviesEffect()
}