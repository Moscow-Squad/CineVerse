package com.di

import com.android.domain.SearchRepository
import com.repository.search.SearchRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

import com.android.domain.repository.SearchRepository
import com.repository.SearchRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import com.android.domain.repository.ExploreRepository
import com.android.domain.repository.MovieRepository
import com.repository.ExploreRepositoryImpl
import com.repository.MovieRepositoryImpl

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl() }

    single<ExploreRepository> {
        ExploreRepositoryImpl(
            searchRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO"))
        )
    }
    single(named("IO")) { Dispatchers.IO }

    single<SearchRepository>{
        SearchRepositoryImpl(
            searchRemoteDataSource = get(),
            get(qualifier = named("IO"))
        )
    }

}