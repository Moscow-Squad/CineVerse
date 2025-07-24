package com.moscow.cineverse.di

import com.android.domain.di.useCaseModule
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