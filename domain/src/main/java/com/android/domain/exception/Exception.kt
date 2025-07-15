package com.android.domain.exception

sealed class CineVerseException : Exception() {
    object NotFoundCineVerseException : CineVerseException()
    object ActorDetailsNotFoundException  : CineVerseException()
    object GalleryNotFoundException  : CineVerseException()
    object BestOfMoviesNotFoundException  : CineVerseException()
    object MappingToDomainException: CineVerseException()
}