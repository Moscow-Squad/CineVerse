package com.moscow.remote.services

import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.media_item.series.SeriesDto
import com.moscow.remote.dto.suggestion.SuggestionDto
import com.moscow.utils.ApiResponse
import com.moscow.utils.INCLUDE_ADULT
import com.moscow.utils.PAGE
import com.moscow.utils.QUERY
import com.moscow.utils.SEARCH_ACTOR
import com.moscow.utils.SEARCH_KEYWORD
import com.moscow.utils.SEARCH_MOVIE
import com.moscow.utils.SEARCH_SERIES
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface SearchService {

    @GET(SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): Response<ApiResponse<MovieDto>>

    @GET(SEARCH_SERIES)
    suspend fun searchSeries(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): Response<ApiResponse<SeriesDto>>

    @GET(SEARCH_ACTOR)
    suspend fun searchActor(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): Response<ApiResponse<ActorDto>>

    @GET(SEARCH_KEYWORD)
    suspend fun getSuggestions(
        @Query(QUERY) keyword: String,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): Response<ApiResponse<SuggestionDto>>
}