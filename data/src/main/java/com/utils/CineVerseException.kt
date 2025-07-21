package com.utils

import com.android.domain.utils.ErrorMessages

open class CineVerseExceptions(code: Int? = null) : Exception()

    class NotFoundCineVerseException : CineVerseExceptions(ErrorMessages.RESOURCE_NOT_FOUND)

    class NoSuggestionFoundException(message: String = ErrorMessages.NO_SUGGESTIONS_FOUND) : CineVerseExceptions(message)

    class IOException(message: String = ErrorMessages.NETWORK_ERROR) : CineVerseExceptions(message)

    class BadRequestException(message: String = ErrorMessages.BAD_REQUEST) : CineVerseExceptions(message)

    class UnauthorizedException(message: String = ErrorMessages.UNAUTHORIZED) :CineVerseExceptions(message)

    class NotFoundException(message: String = ErrorMessages.NOT_FOUND) : CineVerseExceptions(message)

    class TooManyRequestsException(message: String = ErrorMessages.TOO_MANY_REQUESTS) : CineVerseExceptions(message)

    class HttpException(val code: Int, message: String = ErrorMessages.HTTP_ERROR.format(code)) : CineVerseExceptions(message)

    class TimeoutException(message: String = ErrorMessages.TIMEOUT) : CineVerseExceptions(message)

    class UnknownException(
        message: String = ErrorMessages.UNKNOWN_ERROR,
        cause: Throwable? = null,
    ) : CineVerseExceptions(message) {
        init {
            cause?.let { initCause(it) }
        }
    }
