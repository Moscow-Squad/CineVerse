package com.local

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.domain.repository.SearchRepository

class DeleteHistoryQueryWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val searchRepository: SearchRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val query = inputData.getString("query") ?: return Result.failure()
        searchRepository.deleteSearchHistory(query)
        return Result.success()
    }
}