package com.moscow.cineverse.screen.castDetails

sealed class CastDetailsEvent {
    data object NavigateBack : CastDetailsEvent()
    data class ShowError(val message: String) : CastDetailsEvent()
    data class OpenSocialMedia(val platform: String, val url: String) : CastDetailsEvent()
    data class NavigateToMovie(val movieId: Int) : CastDetailsEvent()
    data class NavigateToFullMovieList(val actorId: Int) : CastDetailsEvent()
    data class NavigateToFullGallery(val actorId: Int) : CastDetailsEvent()
}