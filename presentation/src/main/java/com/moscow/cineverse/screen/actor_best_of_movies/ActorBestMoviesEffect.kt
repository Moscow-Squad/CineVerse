package com.moscow.cineverse.screen.actor_best_of_movies

sealed class ActorBestMoviesEffect {
    data class NavigateMovieDetails(val movieId: Int) : ActorBestMoviesEffect()
    data object NavigateBack : ActorBestMoviesEffect()

}