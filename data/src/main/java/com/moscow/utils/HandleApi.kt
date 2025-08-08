package com.moscow.utils

import com.moscow.domain.exception.CineVerseException.BadRequestException
import com.moscow.domain.exception.CineVerseException.ForbiddenRequestException
import com.moscow.domain.exception.CineVerseException.NoInternetException
import com.moscow.domain.exception.CineVerseException.NotFoundException
import com.moscow.domain.exception.CineVerseException.ServerErrorException
import com.moscow.domain.exception.CineVerseException.ServerNotFoundException
import com.moscow.domain.exception.CineVerseException.ServiceUnavailableException
import com.moscow.domain.exception.CineVerseException.TooManyRequestsException
import com.moscow.domain.exception.CineVerseException.TooMuchTimeException
import com.moscow.domain.exception.CineVerseException.UnauthorizedRequestException
import com.moscow.domain.exception.CineVerseException.UnknownException
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <T> handleApi(
    crossinline execute: suspend () -> Response<T>
): T {
    try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            return body
        } else {
            when (response.code()) {
                400 -> throw BadRequestException
                401 -> throw UnauthorizedRequestException
                403 -> throw ForbiddenRequestException
                404 -> throw NotFoundException
                429 -> throw TooManyRequestsException
                500 -> throw ServerErrorException
                503 -> throw ServiceUnavailableException
                else -> throw UnknownException
            }
        }
    } catch (e: ConnectException) {
        throw NoInternetException
    } catch (e: HttpException) {
        when (e.code()) {
            500 -> throw ServerErrorException
            404 -> throw NotFoundException
            else -> throw UnknownException
        }
    } catch (e: SocketTimeoutException) {
        throw TooMuchTimeException
    } catch (e: UnknownHostException) {
        if (e.message?.contains("Unable to resolve host") == true)
            throw NoInternetException
        else
            throw ServerNotFoundException
    } catch (e: Throwable) {
        throw UnknownException
    }
}