package com.remote.source

import com.remote.dto.MovieDto
import com.remote.dto.MultiSearchDto
import com.remote.dto.MultiSearchResponse
import com.remote.dto.ActorDto
import com.remote.dto.SearchResponse
import com.remote.dto.SeriesDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun searchMulti(
        query: String,
    ): List<MultiSearchDto> {
        val response: MultiSearchResponse =
            client.get("https://api.themoviedb.org/3/search/multi") {
                parameter("api_key", "b37d9f568685efadafa2ce0a871597fb")
                parameter("query", query)
                parameter("page", 1)
                parameter("include_adult", false)
            }.body()

        return response.results
    }

    suspend fun searchMovie(query: String): List<MovieDto>{
        val response: SearchResponse<MovieDto> =
            client.get("https://api.themoviedb.org/3/search/movie") {
                parameter("api_key", "b37d9f568685efadafa2ce0a871597fb")
                parameter("query", query)
                parameter("page", 1)
                parameter("include_adult", false)
            }.body()

        return response.results
    }

    suspend fun searchSeries(query: String): List<SeriesDto>{
        val response: SearchResponse<SeriesDto> =
            client.get("https://api.themoviedb.org/3/search/tv") {
                parameter("api_key", "b37d9f568685efadafa2ce0a871597fb")
                parameter("query", query)
                parameter("page", 1)
                parameter("include_adult", false)
            }.body()

        return response.results
    }

    suspend fun searchPearson(query: String): List<ActorDto>{
        val response: SearchResponse<ActorDto> =
            client.get("https://api.themoviedb.org/3/search/multi") {
                parameter("api_key", "b37d9f568685efadafa2ce0a871597fb")
                parameter("query", query)
                parameter("page", 1)
                parameter("include_adult", false)
            }.body()

        return response.results
    }
}
