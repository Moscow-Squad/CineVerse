package com.repository.search

import androidx.room.Transaction
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.android.domain.repository.SearchRepository
import com.android.domain.model.Movie
import com.android.domain.model.Suggestion
import com.mapper.toModel
import com.remote.DeleteQueryWorker
import com.remote.source.SearchRemoteDataSource
import com.repository.mapper.toDomain
import com.repository.mapper.toEntity
import com.utils.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit

class SearchRepositoryImpl(
    private val searchLocalDateSource: SearchLocalDateSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager
) : SearchRepository, BaseRepository(
) {
    override suspend fun getMoviesBySearchTerm(searchTerm: String): List<Movie> {
        return searchLocalDateSource.getMoviesBySearchTerm(searchTerm).toDomain()
    }

    @Transaction
    override suspend fun insertMovie(movies: List<Movie>, searchTerm: String) {
        searchLocalDateSource.insertSearchHistory(searchTerm)
        searchLocalDateSource.insertMovie(movies.toEntity(searchTerm), searchTerm)
        val deleteWork = OneTimeWorkRequestBuilder<DeleteQueryWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setInputData(workDataOf("query" to searchTerm))
            .addTag("delete_search_query_history")
            .build()

        workManager.enqueue(deleteWork)
    }
    override suspend fun getLocalSuggestions(): List<String> {
        return searchLocalDateSource.getAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchLocalDateSource.deleteSearchHistory(searchTerm)
    }

    override fun getRemoteSuggestions(keyWord: String,page:Int): Flow<List<Suggestion>> =
        flow {
            val remoteSuggestions = searchRemoteDataSource.getSuggestions(keyWord,page)
            emit(remoteSuggestions.toModel())
        }.flowOn(ioDispatcher)

}