package com.utils

import com.android.domain.exception.CineVerseException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

abstract class BaseRepository {
    protected suspend inline fun <T> tryToExecute(
        crossinline function: suspend () -> T
    ): T {
        return try {
            function()
        } catch (e: CineVerseException) {
            throw e
        } catch (e: IOException) {
            throw CineVerseException.IOException()
        } catch (e: ClientRequestException) {
            throw e.handleHttpException()
        } catch (e: ServerResponseException) {
            throw e.handleHttpException()
        } catch (e: HttpRequestTimeoutException) {
            throw CineVerseException.TimeoutException()
        } catch (e: Exception) {
            throw CineVerseException.UnknownException(cause = e)
        }
    }

     protected fun ClientRequestException.handleHttpException(): CineVerseException =
        handleHttpStatusCode(response.status.value)

    protected fun ServerResponseException.handleHttpException(): CineVerseException =
        handleHttpStatusCode(response.status.value)

    private fun handleHttpStatusCode(statusCode: Int): CineVerseException {
        return when (statusCode) {
            400 -> CineVerseException.BadRequestException()
            401 -> CineVerseException.UnauthorizedException()
            404 -> CineVerseException.NotFoundException()
            429 -> CineVerseException.TooManyRequestsException()
            else -> CineVerseException.HttpException(statusCode)
        }
    }
}