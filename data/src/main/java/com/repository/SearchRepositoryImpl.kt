package com.repository

import com.android.domain.model.Suggestion
import com.android.domain.repository.SearchRepository
import com.mapper.toModel
import com.remote.source.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    ): SearchRepository{


    override fun getRemoteSuggestions(keyWord: String,page:Int): Flow<List<Suggestion>> =
        flow {
            val remoteSuggestions = searchRemoteDataSource.getSuggestions(keyWord,page)
            emit(remoteSuggestions.toModel())
    }.flowOn(ioDispatcher)


    override fun getLocalSuggestions(keyWord: String): Flow<List<Suggestion>> {
        TODO("Not yet implemented")
    }

}