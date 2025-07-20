package com.services

import retrofit2.http.GET
import retrofit2.http.Query
import com.remote.dto.MovieDto
import com.remote.dto.ActorDto
import com.remote.dto.SuggestionDto
import com.remote.dto.details.SeriesDto
import com.utils.*

interface SearchService {
    @GET(SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int = 1,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): ApiResponse<MovieDto>

    @GET(SEARCH_SERIES)
    suspend fun searchSeries(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int = 1,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): ApiResponse<SeriesDto>

    @GET(SEARCH_ACTOR)
    suspend fun searchPearson(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int = 1,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): ApiResponse<ActorDto>

    @GET(SEARCH_KEYWORD)
    suspend fun getSuggestions(
        @Query(QUERY) keyword: String,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): ApiResponse<SuggestionDto>
}