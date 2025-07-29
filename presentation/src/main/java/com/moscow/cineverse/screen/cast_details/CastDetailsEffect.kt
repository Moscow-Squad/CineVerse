package com.moscow.cineverse.screen.cast_details

sealed class CastDetailsEffect {
    data object NavigateBack : CastDetailsEffect()
    data class ShowError(val message: String) : CastDetailsEffect()
    data class OpenSocialMedia(val platform: String, val url: String) : CastDetailsEffect()
    data class NavigateToMovie(val movieId: Int) : CastDetailsEffect()
    data class NavigateToFullMovieList(val actorId: Int, val actorName: String) :
        CastDetailsEffect()
    data class NavigateToFullGallery(val actorId: Int, val actorName: String) : CastDetailsEffect()
}