package com.remote

import android.util.Log
import com.dto.SuggestionResponse
import io.ktor.client.request.get
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter

class SearchRemoteDataSource(
    private val client: HttpClient
){

    suspend fun getSuggestions(keyword:String,page:Int): SuggestionResponse{
        val response: SuggestionResponse =
            client.get("https://api.themoviedb.org/3/search/keyword"){
                parameter("api_key", "b37d9f568685efadafa2ce0a871597fb")
                parameter("query",keyword)
                parameter("page",page)
            }.body()

        return response
    }
}

