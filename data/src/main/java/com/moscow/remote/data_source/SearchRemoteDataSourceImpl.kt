package com.moscow.remote.data_source

import com.moscow.data_source.remote.SearchRemoteDataSource
import com.moscow.remote.services.SearchService
import com.moscow.utils.handleApi

class SearchRemoteDataSourceImpl(
    private val searchService: SearchService
) : SearchRemoteDataSource {

    override suspend fun searchMovie(query: String, page: Int, includeAdult: Boolean) =
        handleApi {
            searchService.searchMovie(query, page, includeAdult)
        }

    override suspend fun searchSeries(query: String, page: Int, includeAdult: Boolean) =
        handleApi {
            searchService.searchSeries(query, page, includeAdult)
        }

    override suspend fun searchActor(query: String, page: Int, includeAdult: Boolean) =
        handleApi {
            searchService.searchActor(query, page, includeAdult)
        }

    override suspend fun searchByKeyword(keyword: String, page: Int, includeAdult: Boolean) =
        handleApi {
            searchService.getSuggestions(keyword, page, includeAdult)
        }
}
