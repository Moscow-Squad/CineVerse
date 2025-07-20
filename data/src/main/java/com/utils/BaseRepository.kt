package com.utils

import com.android.domain.exception.CineVerseException


abstract class BaseRepository {
    protected suspend inline fun <T> tryToExecute(
        crossinline function: suspend () -> T
    ): T {
        return try {
            function()
        } catch (e: CineVerseException) {
            throw e
        }
         catch (e: IOException) {
            throw CineVerseExceptions.IOException()
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                400 -> throw CineVerseExceptions.BadRequestException()
                401 -> throw CineVerseExceptions.UnauthorizedException()
                404 -> throw CineVerseExceptions.NotFoundException()
                429 -> throw CineVerseExceptions.TooManyRequestsException()
                else -> throw CineVerseExceptions.HttpException(e.response.status.value)
            }
        } catch (e: ServerResponseException) {
            when (e.response.status.value) {
                400 -> throw CineVerseExceptions.BadRequestException()
                401 -> throw CineVerseExceptions.UnauthorizedException()
                404 -> throw CineVerseExceptions.NotFoundException()
                429 -> throw CineVerseExceptions.TooManyRequestsException()
                else -> throw CineVerseExceptions.HttpException(e.response.status.value)
            }
        } catch (e: HttpRequestTimeoutException) {
            throw CineVerseExceptions.TimeoutException()
        } catch (e: Exception) {
            throw CineVerseExceptions.UnknownException(cause = e)
        }
    }
}