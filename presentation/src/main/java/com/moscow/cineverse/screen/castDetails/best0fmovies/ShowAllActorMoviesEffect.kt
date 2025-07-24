package com.moscow.cineverse.screen.castDetails.best0fmovies

sealed class ShowAllActorMoviesEffect {
    data class NavigateMovieDetails(val movieId: Int) : ShowAllActorMoviesEffect()
}