package com.moscow.cineverse.screen.actor_best_of_movies


object ActorBestMoviesEffectHandler {
    fun handleEffect(
        effect: ActorBestMoviesEffect,
        navigateMovieDetails:(Int) -> Unit,
        navigateBack: () -> Unit,
        ) {
        when (effect) {
            is ActorBestMoviesEffect.NavigateMovieDetails -> {
                navigateMovieDetails(effect.movieId)
            }
            ActorBestMoviesEffect.NavigateBack -> {
                navigateBack()
            }
        }
    }
}