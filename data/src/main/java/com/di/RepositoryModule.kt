package com.di

import com.android.domain.repository.SearchRepository
import com.remote.SearchRemoteDataSource
import com.repository.SearchRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchRepository>{
        SearchRepositoryImpl(
            searchRemoteDataSource = get(),
            get(qualifier = named("IO"))
        )
    }
    single(named("IO")){ Dispatchers.IO }
}