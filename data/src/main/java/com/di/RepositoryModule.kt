package com.di

import com.android.domain.repository.ExploreRepository
import com.android.domain.repository.MovieRepository
import com.repository.ExploreRepositoryImpl
import com.repository.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl() }
    single<ExploreRepository> {
        ExploreRepositoryImpl(
            searchRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO"))
        )
    }
    single(named("IO")) { Dispatchers.IO }
}