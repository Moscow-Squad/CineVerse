package com.repository.explore.search

import androidx.room.Transaction
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.android.domain.exception.CineVerseException
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
import kotlin.collections.sortedWith

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
            .sortMoviesByFavouriteGenre()
    }

    @Transaction
    override suspend fun insertMovie(movies: List<Movie>, searchTerm: String) {
        searchLocalDateSource.insertSearchHistory(searchTerm)
        searchLocalDateSource.insertMovie(movies.toEntity(searchTerm), searchTerm)
        val deleteWork = OneTimeWorkRequestBuilder<DeleteQueryWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .setInputData(workDataOf(QUERY to searchTerm))
            .addTag(DELETE_SEARCH_QUERY_HISTORY)
            .build()

        workManager.enqueue(deleteWork)
    }

    override suspend fun insertActors(actors: List<Actor>, searchTerm: String) {
        searchLocalDateSource.insertSearchHistory(searchTerm)
        searchLocalDateSource.insertActors(actors.toEntity(searchTerm), searchTerm)
        val deleteWork = OneTimeWorkRequestBuilder<DeleteQueryWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .setInputData(workDataOf(QUERY to searchTerm))
            .addTag(DELETE_SEARCH_QUERY_HISTORY)
            .build()

        workManager.enqueue(deleteWork)
    }

    override suspend fun insertSeries(series: List<Series>, searchTerm: String) {
        searchLocalDateSource.insertSearchHistory(searchTerm)
        searchLocalDateSource.insertSeries(series.toEntity(searchTerm), searchTerm)
        val deleteWork = OneTimeWorkRequestBuilder<DeleteQueryWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .setInputData(workDataOf(QUERY to searchTerm))
            .addTag(DELETE_SEARCH_QUERY_HISTORY)
            .build()

        workManager.enqueue(deleteWork)
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
            val result = tryToExecute {
                searchRemoteDataSource.searchMulti(query)
            }
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() })
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
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
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() }.sortMoviesByFavouriteGenre())
                insertMovie(result.map { movie -> movie.toDomain() }, query)
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
        }.flowOn(ioDispatcher)

    override suspend fun searchSeries(query: String, isHistory: Boolean): Flow<List<Series>> =
        flow {
            if (isHistory) {
                emit(
                    searchLocalDateSource
                    .getSeriesBySearchTerm(query)
                    .toDomain()
                    .sortSeriesByFavouriteGenre()
                )
                return@flow
            }
            val result = tryToExecute {
                searchRemoteDataSource.searchSeries(query)
            }
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() }.sortSeriesByFavouriteGenre())
                insertSeries(result.map { series -> series.toDomain() }, query)
            } else {
                throw CineVerseException.NotFoundCineVerseException
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
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() })
                insertActors(result.map { actor -> actor.toDomain() }, query)
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
        }.flowOn(ioDispatcher)

    private suspend fun List<Movie>.sortMoviesByFavouriteGenre(): List<Movie>{
        val favouriteGenres = searchLocalDateSource.getFavouriteGenre().toSortedGenres()
        if (favouriteGenres.isEmpty()) return this
        return this.sortedWith(compareBy(
            { movie ->
                -movie.genreIds.count { genre -> genre in favouriteGenres }
            },
            { movie ->
                movie.genreIds.mapNotNull { genre -> favouriteGenres.indexOf(genre).takeIf { it != -1 } }.minOrNull() ?: Int.MAX_VALUE
            }
        ))
    }

    private suspend fun List<Series>.sortSeriesByFavouriteGenre(): List<Series>{
        val favouriteGenres = searchLocalDateSource.getFavouriteGenre().toSortedGenres()
        if (favouriteGenres.isEmpty()) return this
        return this.sortedWith(compareBy(
            { series ->
                -series.genreIds.count { genre -> genre in favouriteGenres }
            },
            { series ->
                series.genreIds.mapNotNull { genre -> favouriteGenres.indexOf(genre).takeIf { it != -1 } }.minOrNull() ?: Int.MAX_VALUE
            }
        ))
    }
}
