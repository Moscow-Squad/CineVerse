package com.repository

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.SearchRepository
import com.data_source.local.SearchLocalDataSource
import com.data_source.remote.SearchRemoteDataSource
import com.local.DeleteHistoryQueryWorker
import com.mapper.toDomain
import com.mapper.toEntity
import com.mapper.toModel
import com.mapper.toSortedGenres
import com.utils.DELETE_SEARCH_QUERY_HISTORY
import com.utils.QUERY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit

class SearchRepositoryImpl(
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val workManager: WorkManager
) : SearchRepository {
    override suspend fun getLocalMoviesBySearchTerm(searchTerm: String): List<Movie> {
        return searchLocalDataSource
            .getMoviesBySearchTerm(searchTerm)
            .sortByFavouriteGenres { it.genresId }
            .toDomain()
    }

    override suspend fun insertMovie(movies: List<Movie>, searchTerm: String) {
        searchLocalDataSource.insertMovie(movies.toEntity(searchTerm), searchTerm)

    }

    override suspend fun insertActors(actors: List<Actor>, searchTerm: String) {
        searchLocalDataSource.insertActors(actors.toEntity(searchTerm), searchTerm)
    }

    override suspend fun insertSeries(series: List<Series>, searchTerm: String) {
        searchLocalDataSource.insertSeries(series.toEntity(searchTerm), searchTerm)
    }

    override suspend fun getLocalSuggestions(): Flow<List<String>> {
        return searchLocalDataSource.getAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchLocalDataSource.deleteSearchHistory(searchTerm)
    }

    override suspend fun getRemoteSuggestions(keyWord: String, page: Int): List<String> {
        return searchRemoteDataSource.searchByKeyword(
            keyWord,
            page,
            false
        ).results?.map { it.toModel() } ?: emptyList()
    }

    override suspend fun searchMovie(query: String, page: Int, isHistory: Boolean): Flow<List<Movie>> =
        flow {
            if (isHistory) {
                emit(getLocalMoviesBySearchTerm(query))
                return@flow
            }
            val result = searchRemoteDataSource.searchMovie(query, page, false)

            val mappedResult = result.results?.sortByFavouriteGenres { it.genreIds ?: emptyList() }
                ?.map { it.toDomain() } ?: emptyList()
            emit(mappedResult)
            if (mappedResult.isNotEmpty()) {
                insertMovie(mappedResult, query)
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun searchSeries(query: String, page: Int, isHistory: Boolean): Flow<List<Series>> =
        flow {
            if (isHistory) {
                emit(
                    searchLocalDataSource
                        .getSeriesBySearchTerm(query)
                        .sortByFavouriteGenres { it.genresId }
                        .toDomain()

                )
                return@flow
            }
            val result = searchRemoteDataSource.searchSeries(query, page, false)

            val mappedResult = result.results?.sortByFavouriteGenres { it.genreIds ?: emptyList() }
                ?.map { it.toDomain() } ?: emptyList()
            emit(mappedResult)
            if (mappedResult.isNotEmpty()) {
                insertSeries(mappedResult, query)
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun searchActor(query: String, page: Int, isHistory: Boolean): Flow<List<Actor>> =
        flow {
            if (isHistory) {
                emit(searchLocalDataSource.getActorsBySearchTerm(query).toDomain())
                return@flow
            }
            val result = searchRemoteDataSource.searchActor(query, page, false)

            val mappedResult = result.results?.map { it.toDomain() } ?: emptyList()
            emit(mappedResult)
            if (mappedResult.isNotEmpty()) {
                insertActors(mappedResult, query)
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun cacheSearchQuery(query: String) {
        searchLocalDataSource.insertSearchHistory(query)
        val deleteWork = OneTimeWorkRequestBuilder<DeleteHistoryQueryWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .setInputData(workDataOf(QUERY to query))
            .addTag(DELETE_SEARCH_QUERY_HISTORY)
            .build()
        workManager.enqueue(deleteWork)
    }

    override suspend fun clearSearchHistory() {
        searchLocalDataSource.deleteAllSearchHistory()
    }

    private suspend fun <T> List<T>.sortByFavouriteGenres(
        getGenreIds: (T) -> List<Int>
    ): List<T> {
        val favouriteGenres = searchLocalDataSource.getFavouriteGenres().toSortedGenres()
        if (favouriteGenres.isEmpty()) return this

        return this.sortedWith(
            compareBy(
                { item ->
                    -getGenreIds(item).count { genre -> genre in favouriteGenres }
                },
                { item ->
                    getGenreIds(item)
                        .mapNotNull { genre -> favouriteGenres.indexOf(genre).takeIf { it != -1 } }
                        .minOrNull() ?: Int.MAX_VALUE
                }
            )
        )
    }
}
