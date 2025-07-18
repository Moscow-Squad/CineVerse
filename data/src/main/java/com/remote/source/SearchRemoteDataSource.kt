package com.remote.source

import com.remote.dto.ActorDto
import com.remote.dto.MovieDto
import com.remote.dto.MultiSearchDto
import com.remote.dto.SuggestionDto
import com.remote.dto.details.SeriesDto
import com.utils.ApiResponse
import com.utils.INCLUDE_ADULT
import com.utils.PAGE
import com.utils.QUERY
import com.utils.SEARCH_ACTOR
import com.utils.SEARCH_KEYWORD
import com.utils.SEARCH_MOVIE
import com.utils.SEARCH_MULTI
import com.utils.SEARCH_SERIES
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

class SearchRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun searchMulti(query: String): List<MultiSearchDto> =
        client.performCall<Unit, ApiResponse<MultiSearchDto>>(
            method = HttpMethod.Get,
            path = SEARCH_MULTI,
            requestBuilder = {
                parameter(QUERY, query)
                parameter(PAGE, 1)
                parameter(INCLUDE_ADULT, false)
            }
        ).results


    suspend fun searchMovie(query: String): List<MovieDto> =
        client.performCall<Unit, ApiResponse<MovieDto>>(
            method = HttpMethod.Get,
            path = SEARCH_MOVIE,
            requestBuilder = {
                parameter(QUERY, query)
                parameter(PAGE, 1)
                parameter(INCLUDE_ADULT, false)
            }
        ).results


    suspend fun searchSeries(query: String): List<SeriesDto> =
        client.performCall<Unit, ApiResponse<SeriesDto>>(
            method = HttpMethod.Get,
            path = SEARCH_SERIES,
            requestBuilder = {
                parameter(QUERY, query)
                parameter(PAGE, 1)
                parameter(INCLUDE_ADULT, false)
            }
        ).results


    suspend fun searchPearson(query: String): List<ActorDto> =
        client.performCall<Unit, ApiResponse<ActorDto>>(
            method = HttpMethod.Get,
            path = SEARCH_ACTOR,
            requestBuilder = {
                parameter(QUERY, query)
                parameter(PAGE, 1)
                parameter(INCLUDE_ADULT, false)
            }
        ).results


    suspend fun getSuggestions(keyword: String, page: Int): List<SuggestionDto> =
        client.performCall<Unit, ApiResponse<SuggestionDto>>(
            method = HttpMethod.Get,
            path = SEARCH_KEYWORD,
            requestBuilder = {
                parameter(QUERY, keyword)
                parameter(PAGE, page)
                parameter(INCLUDE_ADULT, false)
            }
        ).results

}
