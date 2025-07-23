package com.data_source.remote

import com.remote.dto.MovieDto
import com.remote.dto.actor.ActorDto
import com.remote.dto.series.SeriesDto
import com.remote.dto.suggestion.SuggestionDto
import com.utils.ApiResponse

interface SearchRemoteDataSource {
    suspend fun searchMovie(query: String, page: Int, includeAdult: Boolean): ApiResponse<MovieDto>
    suspend fun searchSeries(query: String, page: Int, includeAdult: Boolean): ApiResponse<SeriesDto>
    suspend fun searchActor(query: String, page: Int, includeAdult: Boolean): ApiResponse<ActorDto>
    suspend fun searchByKeyword(keyword: String, page: Int, includeAdult: Boolean): ApiResponse<SuggestionDto>
}