package com.moscow.local.data_source

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.moscow.data_source.local.SearchLocalDataSource
import com.moscow.local.DeleteHistoryQueryWorker
import com.moscow.local.dao.search.FavouriteGenreDao
import com.moscow.local.dao.search.SearchHistoryDao
import com.moscow.local.entity.FavouriteGenreEntity
import com.moscow.local.entity.SearchHistoryEntity
import com.moscow.utils.DELETE_SEARCH_QUERY_HISTORY
import com.moscow.utils.QUERY
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val favouriteGenreDao: FavouriteGenreDao,
    private val workManager: WorkManager
) : SearchLocalDataSource {

    override suspend fun getAllSearchHistory(): Flow<List<String>> =
        searchHistoryDao.getAllSearchHistory()

    override suspend fun insertSearchHistory(
        searchTerm: String
    ) {
        searchHistoryDao.insertSearchHistory(
            SearchHistoryEntity(searchTerm = searchTerm)
        )
        val deleteWork = OneTimeWorkRequestBuilder<DeleteHistoryQueryWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setInputData(workDataOf(QUERY to searchTerm))
            .addTag(DELETE_SEARCH_QUERY_HISTORY)
            .build()
        workManager.enqueue(deleteWork)
    }

    override suspend fun deleteSearchHistory(
        searchTerm: String
    ) = searchHistoryDao.deleteSearchHistory(
        SearchHistoryEntity(searchTerm = searchTerm)
    )

    override suspend fun deleteAllSearchHistory() = searchHistoryDao.deleteAllSearchHistory()


    override fun getFavouriteGenres(): Flow<List<FavouriteGenreEntity>> =
        favouriteGenreDao.getFavouriteGenres()


    override suspend fun insertFavouriteGenre(genreId: Int) {
        val existingGenre = favouriteGenreDao.getGenreById(genreId = genreId)
        if (existingGenre != null) {
            favouriteGenreDao.incrementGenreCount(genreId = genreId)
        } else {
            favouriteGenreDao.insertOrUpdateFavouriteGenre(
                FavouriteGenreEntity(
                    genreId = genreId,
                    count = 1
                )
            )
        }
    }
}