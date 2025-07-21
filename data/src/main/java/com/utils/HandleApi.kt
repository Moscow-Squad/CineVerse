package com.utils

import retrofit2.HttpException
import retrofit2.Response

suspend inline fun <reified T > handleApi(
    crossinline execute: suspend () -> Response<T>
): T {
    try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            return body
        } else {
            val errorBody = response.errorBody()?.string()
            throw CineVerseExceptions(response.code(), errorBody ?: "Unexpected error happened")
        }
    } catch (e: HttpException) {
        throw CineVerseExceptions(e.code(), e.message())
    } catch (e: Throwable) {
        throw CineVerseExceptions(0, e.message?: "Unexpected error happened")
    }
}
