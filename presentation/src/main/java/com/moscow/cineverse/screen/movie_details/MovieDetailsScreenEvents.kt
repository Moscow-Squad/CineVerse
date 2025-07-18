package com.moscow.cineverse.screen.movie_details

sealed class MovieDetailsScreenEvents {
    data object NavigateBack : MovieDetailsScreenEvents()
    data class ShowError(val message: String) : MovieDetailsScreenEvents()
    data class NavigateToFullMovieList(val movieID:Int, val movieTitle:String) : MovieDetailsScreenEvents()
    data class NavigateToFullReviews(val movieID: Int) : MovieDetailsScreenEvents()
    data object NavigateToFullActors: MovieDetailsScreenEvents()
    data object NavigateToFullCast : MovieDetailsScreenEvents()
    data class AddToCollection(val movieId: Int) : MovieDetailsScreenEvents()
}

