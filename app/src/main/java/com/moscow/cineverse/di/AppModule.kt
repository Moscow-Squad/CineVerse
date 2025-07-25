package com.moscow.cineverse.di

import com.android.domain.di.useCaseModule
import com.moscow.di.dataModule
import com.moscow.di.repositoryModule
import com.moscow.di.workManagerModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        workManagerModule,
        repositoryModule,
        useCaseModule,
        viewModelModule
    )
}