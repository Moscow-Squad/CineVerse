package com.di

import com.android.domain.MovieRepository
import com.remote.mapper.MovieMapper
import com.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieMapper() }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}

