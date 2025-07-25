package com.moscow.domain.exception

import com.moscow.domain.utils.ErrorMessages


sealed class CineVerseException(override val message: String? = null) : Exception(message) {

   data class IOException(override val message: String = ErrorMessages.NETWORK_ERROR) : CineVerseException()

    class BadRequestException(message: String = ErrorMessages.BAD_REQUEST) :
        CineVerseException(message)

    class UnauthorizedException(message: String = ErrorMessages.UNAUTHORIZED) :
        CineVerseException(message)

    class NotFoundException(message: String = ErrorMessages.NOT_FOUND) :
        CineVerseException(message)

    class TooManyRequestsException(message: String = ErrorMessages.TOO_MANY_REQUESTS) :
        CineVerseException(message)

    class HttpException(val code: Int, message: String = ErrorMessages.HTTP_ERROR.format(code)) :
        CineVerseException(message)

    class TimeoutException(message: String = ErrorMessages.TIMEOUT) : CineVerseException(message)

    class UnknownException(
        message: String = ErrorMessages.UNKNOWN_ERROR,
        cause: Throwable? = null,
    ) : CineVerseException(message) {
        init {
            cause?.let { initCause(it) }
        }
    }
    data  object MappingToDomainException: CineVerseException()
}

