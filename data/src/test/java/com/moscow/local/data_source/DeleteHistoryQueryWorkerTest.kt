package com.moscow.local.data_source

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import androidx.work.ListenableWorker.Result
import com.moscow.domain.repository.SearchRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteHistoryQueryWorkerTest {

    private lateinit var searchRepository: SearchRepository
    private lateinit var context: Context
    private lateinit var workerParams: WorkerParameters

    @BeforeEach
    fun setup() {
        searchRepository = mockk(relaxed = true)
        context = mockk(relaxed = true)
        workerParams = mockk(relaxed = true)
    }

    @Test
    fun `doWork should delete query and return success`() = runTest {
        val query = "Inception"
        val inputData = workDataOf("query" to query)

        every { workerParams.inputData } returns inputData

        val worker = DeleteHistoryQueryWorker(context, workerParams, searchRepository)
        val result = worker.doWork()

        assertTrue(result is Result.Success)
        coVerify { searchRepository.deleteSearchHistory(query) }
    }

    @Test
    fun `doWork should return failure if query is missing`() = runTest {
        val inputData = workDataOf()

        every { workerParams.inputData } returns inputData

        val worker = DeleteHistoryQueryWorker(context, workerParams, searchRepository)
        val result = worker.doWork()

        assertTrue(result is Result.Failure)
        coVerify(exactly = 0) { searchRepository.deleteSearchHistory(any()) }
    }
}