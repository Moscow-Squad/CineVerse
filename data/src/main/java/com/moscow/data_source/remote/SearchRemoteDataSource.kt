package com.moscow.data_source.remote

import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.actor.ActorDto
import com.moscow.remote.dto.media_item.series.SeriesDto
import com.moscow.remote.dto.suggestion.SuggestionDto
import com.moscow.utils.ApiResponse

interface SearchRemoteDataSource {
    suspend fun searchMovie(query: String, page: Int, includeAdult: Boolean): ApiResponse<MovieDto>
    suspend fun searchSeries(query: String, page: Int, includeAdult: Boolean): ApiResponse<SeriesDto>
    suspend fun searchActor(query: String, page: Int, includeAdult: Boolean): ApiResponse<ActorDto>
    suspend fun searchByKeyword(keyword: String, page: Int, includeAdult: Boolean): ApiResponse<SuggestionDto>
}