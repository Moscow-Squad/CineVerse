package com.remote.source


import com.remote.services.SearchService
import com.utils.handleApi


class SearchRemoteDataSource(private val searchService: SearchService) {

     suspend fun searchMovie(query: String, page: Int, includeAdult: Boolean) = handleApi {
         searchService.searchMovie(query, page, includeAdult)
    }

     suspend fun searchSeries(query: String, page: Int, includeAdult: Boolean)= handleApi {
         searchService.searchSeries(query, page, includeAdult)
     }

     suspend fun searchPerson(query: String, page: Int, includeAdult: Boolean) = handleApi {
         searchService.searchPearson(query, page, includeAdult)
    }

     suspend fun getSuggestions(keyword: String, page: Int, includeAdult: Boolean) = handleApi {
         searchService.getSuggestions(keyword, page, includeAdult)
    }

}
