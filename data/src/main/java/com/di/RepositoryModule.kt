package com.di

import com.android.domain.repository.MovieRepository
import com.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single <MovieRepository> { MovieRepositoryImpl() }
}