package com.remote.services

import retrofit2.http.GET
import retrofit2.http.Query
import com.remote.dto.MovieDto
import com.remote.dto.actor.ActorDto
import com.remote.dto.suggestion.SuggestionDto
import com.remote.dto.series.SeriesDto
import com.utils.*
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