package com.android.domain.exception

sealed class CineVerseException : Exception() {
    object NotFoundCineVerseException : CineVerseException()
    class NoSuggestionFoundException() : CineVerseException()
    object NoGalleryFoundException : CineVerseException()
    object NoMoviesFoundException : CineVerseException()
}
