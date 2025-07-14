package com.di

import androidx.work.WorkManager
import com.local.DeleteQueryWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val workManagerModule = module {
    single { WorkManager.getInstance(androidContext()) }
    workerOf(::DeleteQueryWorker)
}