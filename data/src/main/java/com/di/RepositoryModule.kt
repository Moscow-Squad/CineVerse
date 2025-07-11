package com.di

import com.android.domain.repository.SearchRepository
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import com.android.domain.repository.ExploreRepository
import com.repository.ExploreRepositoryImpl
import com.repository.search.SearchRepositoryImpl

val repositoryModule = module {

    single<ExploreRepository> {
        ExploreRepositoryImpl(
            searchRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO"))
        )
    }
    single(named("IO")) { Dispatchers.IO }

    single<SearchRepository> {
        SearchRepositoryImpl(
            searchRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO")),
            searchLocalDateSource = get(),
            workManager = get()
        )
    }
}