package com.moscow.cineverse.screen.movie_details

sealed class MovieDetailsScreenEffect {
    data object NavigateBack : MovieDetailsScreenEffect()
    data class ShowError(val message: String) : MovieDetailsScreenEffect()
    data class NavigateToFullMovieList(val movieID: Int, val movieTitle: String) :
        MovieDetailsScreenEffect()

    data class NavigateToFullReviews(val movieID: Int) : MovieDetailsScreenEffect()
    data object NavigateToFullActors : MovieDetailsScreenEffect()
    data object NavigateToFullCast : MovieDetailsScreenEffect()
    data class AddToCollection(val movieId: Int) : MovieDetailsScreenEffect()
    data class NavigateCastDetails(val castId: Int) : MovieDetailsScreenEffect()
    data class NavigateToMovieDetails(val movieId: Int) : MovieDetailsScreenEffect()
}