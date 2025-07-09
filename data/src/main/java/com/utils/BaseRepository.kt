package com.utils

import com.exception.CineVerseException

abstract class BaseRepository {
    protected suspend fun <T> wrapResponse(
        exception: CineVerseException,
        function: suspend () -> T
    ): T {
        return try {
            function()
        } catch (e: Exception) {
            throw exception
        }
    }
}