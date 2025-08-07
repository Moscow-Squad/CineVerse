package com.moscow.domain.exception

sealed class CineVerseException() : Exception() {
    object BadRequestException : CineVerseException()
    object UnauthorizedRequestException : CineVerseException()
    object ForbiddenRequestException : CineVerseException()
    object NotFoundException : CineVerseException()
    object ServerErrorException : CineVerseException()
    object UnknownException : CineVerseException()
    object NoInternetException : CineVerseException()
    object TooMuchTimeException : CineVerseException()
    object ServerNotFoundException : CineVerseException()
    object ServiceUnavailableException : CineVerseException()
    object TooManyRequestsException : CineVerseException()
    object NullException : CineVerseException()
    object AddMediaItemToCollectionException : CineVerseException()
    object DeleteMediaItemFromCollectionException : CineVerseException()
    object ClearCollectionException : CineVerseException()
    object NotAllowedUserException : CineVerseException()
}