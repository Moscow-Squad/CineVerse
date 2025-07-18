package com.repository.explore.search

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import com.android.domain.repository.SearchRepository
import com.local.DeleteQueryWorker
import com.mapper.toDomain
import com.mapper.toModel
import com.remote.source.SearchRemoteDataSource
import com.repository.mapper.toDomain
import com.repository.mapper.toEntity
import com.repository.mapper.toSortedGenres
import com.utils.BaseRepository
import com.utils.DELETE_SEARCH_QUERY_HISTORY
import com.utils.QUERY
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
    override suspend fun getLocalMoviesBySearchTerm(searchTerm: String): List<Movie> {
        return searchLocalDateSource
            .getMoviesBySearchTerm(searchTerm)
            .toDomain()
            .sortByFavouriteGenres{it.genreIds}
    }

    override suspend fun insertMovie(movies: List<Movie>, searchTerm: String) {
        searchLocalDateSource.insertMovie(movies.toEntity(searchTerm), searchTerm)

    }

    override suspend fun insertActors(actors: List<Actor>, searchTerm: String) {
        searchLocalDateSource.insertActors(actors.toEntity(searchTerm), searchTerm)
    }

    override suspend fun insertSeries(series: List<Series>, searchTerm: String) {
        searchLocalDateSource.insertSeries(series.toEntity(searchTerm), searchTerm)
    }

    override suspend fun getLocalSuggestions(): Flow<List<String>> {
        return searchLocalDateSource.getAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(searchTerm: String) {
        searchLocalDateSource.deleteSearchHistory(searchTerm)
    }

    override suspend fun getRemoteSuggestions(keyWord: String, page: Int): List<String> =
        tryToExecute {
            searchRemoteDataSource.getSuggestions(keyWord, page)
        }.map { it.toModel() }

    override suspend fun searchMulti(query: String): Flow<List<MultiSearch>> =
        flow {
            emit(
                tryToExecute {
                    searchRemoteDataSource.searchMulti(query)
                }.map { it.toDomain() }
            )
        }.flowOn(ioDispatcher)

    override suspend fun searchMovie(query: String, isHistory: Boolean): Flow<List<Movie>> =
        flow {
            if (isHistory) {
                emit(getLocalMoviesBySearchTerm(query))
                return@flow
            }
            val result = tryToExecute {
                searchRemoteDataSource.searchMovie(query)
            }
            emit(result.map { it.toDomain() }.sortByFavouriteGenres{it.genreIds})
            if (result.isNotEmpty()) {
                insertMovie(result.map { movie -> movie.toDomain() }, query)
            }
        }.flowOn(ioDispatcher)

    override suspend fun searchSeries(query: String, isHistory: Boolean): Flow<List<Series>> =
        flow {
            if (isHistory) {
                emit(
                    searchLocalDateSource
                    .getSeriesBySearchTerm(query)
                    .toDomain()
                    .sortByFavouriteGenres{it.genreIds}
                )
                return@flow
            }
            val result = tryToExecute {
                searchRemoteDataSource.searchSeries(query)
            }
            emit(result.map { it.toDomain() }.sortByFavouriteGenres{it.genreIds})
            if (result.isNotEmpty()) {
                insertSeries(result.map { series -> series.toDomain() }, query)
            }
        }.flowOn(ioDispatcher)

    override suspend fun searchActor(query: String, isHistory: Boolean): Flow<List<Actor>> =
        flow {
            if (isHistory) {
                emit(searchLocalDateSource.getActorsBySearchTerm(query).toDomain())
                return@flow
            }
            val result = tryToExecute {
                searchRemoteDataSource.searchPearson(query)
            }
            emit(result.map { it.toDomain() })
            if (result.isNotEmpty()) {
                insertActors(result.map { actor -> actor.toDomain() }, query)
            }
        }.flowOn(ioDispatcher)

    override suspend fun cacheSearchQuery(query: String) {
        tryToExecute {
            searchLocalDateSource.insertSearchHistory(query)
            val deleteWork = OneTimeWorkRequestBuilder<DeleteQueryWorker>()
                .setInitialDelay(1, TimeUnit.HOURS)
                .setInputData(workDataOf(QUERY to query))
                .addTag(DELETE_SEARCH_QUERY_HISTORY)
                .build()
            workManager.enqueue(deleteWork)
        }
    }

    override suspend fun clearSearchHistory() = tryToExecute {
        searchLocalDateSource.deleteAllSearchHistory()
    }

    private suspend fun <T> List<T>.sortByFavouriteGenres(
        getGenreIds: (T) -> List<Int>
    ): List<T> {
        val favouriteGenres = searchLocalDateSource.getFavouriteGenres().toSortedGenres()
        if (favouriteGenres.isEmpty()) return this

        return this.sortedWith(compareBy(
            { item ->
                -getGenreIds(item).count { genre -> genre in favouriteGenres }
            },
            { item ->
                getGenreIds(item)
                    .mapNotNull { genre -> favouriteGenres.indexOf(genre).takeIf { it != -1 } }
                    .minOrNull() ?: Int.MAX_VALUE
            }
        ))
    }
}
