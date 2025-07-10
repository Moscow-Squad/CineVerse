package com.repository.search

import androidx.room.Transaction
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.android.domain.SearchRepository
import com.android.domain.model.Movie
import com.remote.DeleteQueryWorker
import com.repository.mapper.toDomain
import com.repository.mapper.toEntity
import com.utils.BaseRepository
import java.util.concurrent.TimeUnit

class SearchRepositoryImpl(
    private val searchLocalDateSource: SearchLocalDateSource,
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
    override suspend fun getSearchHistory(): List<String> {
        return searchLocalDateSource.getAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchLocalDateSource.deleteSearchHistory(searchTerm)
    }

}