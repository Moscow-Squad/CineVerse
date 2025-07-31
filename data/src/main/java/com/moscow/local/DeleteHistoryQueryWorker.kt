package com.moscow.local

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.moscow.domain.repository.SearchRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DeleteHistoryQueryWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val searchRepository: SearchRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val query = inputData.getString("query") ?: return Result.failure()
        searchRepository.deleteSearchHistory(query)
        return Result.success()
    }
}