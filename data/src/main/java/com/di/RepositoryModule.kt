package com.di

import com.android.domain.repository.ActorRepository
import com.android.domain.repository.CollectionsRepository
import com.android.domain.repository.GenreRepository
import com.android.domain.repository.HomeRepository
import com.android.domain.repository.LoginRepository
import com.android.domain.repository.MovieRepository
import com.android.domain.repository.PreferenceRepository
import com.android.domain.repository.SearchRepository
import com.android.domain.repository.SeriesRepository
import com.repository.ActorRepositoryImpl
import com.repository.CollectionsRepositoryImpl
import com.repository.GenreRepositoryImpl
import com.repository.HomeRepositoryImpl
import com.repository.MovieRepositoryImpl
import com.repository.SearchRepositoryImpl
import com.repository.SeriesRepositoryImpl
import com.repository.login.LoginRepositoryImpl
import com.repository.preference.PreferenceRepositoryImpl
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
    singleOf(::LoginRepositoryImpl) bind LoginRepository::class
    singleOf(::PreferenceRepositoryImpl) bind PreferenceRepository::class
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
}