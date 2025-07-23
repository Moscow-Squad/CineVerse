package com.di

import com.android.domain.repository.ActorRepository
import com.android.domain.repository.CollectionsRepository
import com.android.domain.repository.GenreRepository
import com.android.domain.repository.MovieRepository
import com.android.domain.repository.SearchRepository
import com.android.domain.repository.SeriesRepository
import com.repository.ActorRepositoryImpl
import com.repository.CollectionsRepositoryImpl
import com.repository.GenreRepositoryImpl
import com.repository.MovieRepositoryImpl
import com.repository.SearchRepositoryImpl
import com.repository.SeriesRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::SeriesRepositoryImpl) bind SeriesRepository::class
    singleOf(::GenreRepositoryImpl) bind GenreRepository::class
    singleOf(::ActorRepositoryImpl) bind ActorRepository::class
    singleOf(::CollectionsRepositoryImpl) bind CollectionsRepository::class
}