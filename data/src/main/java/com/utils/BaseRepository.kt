package com.utils

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
        } catch (e: IOException) {
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
    }}