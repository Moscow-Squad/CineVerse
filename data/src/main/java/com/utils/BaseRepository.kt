package com.utils

import com.android.domain.exception.CineVerseException


abstract class BaseRepository {
    protected suspend inline fun <T> tryToExecute(
        exception: CineVerseException,
        crossinline function: suspend () -> T
    ): T {
        return try {
            function()
        } catch (e: CineVerseException) {
            throw e
        }
        catch (e: Exception) {
            throw exception
        }
    }
}