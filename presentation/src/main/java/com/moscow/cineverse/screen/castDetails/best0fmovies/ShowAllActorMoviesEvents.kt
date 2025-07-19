package com.moscow.cineverse.screen.castDetails.best0fmovies

sealed class ShowAllActorMoviesEvents {
    data class NavigateMovieDetails(val movieId: Int) : ShowAllActorMoviesEvents()
}