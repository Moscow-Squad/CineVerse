package com.moscow.di

import com.moscow.data_source.language.LanguageProvider
import com.moscow.data_source.theme.ThemeProvider
import com.moscow.domain.repository.ActorRepository
import com.moscow.domain.repository.CollectionsRepository
import com.moscow.domain.repository.GenreRepository
import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.repository.LoginRepository
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.domain.repository.SearchRepository
import com.moscow.domain.repository.SeriesRepository
import com.moscow.preference.LanguageProviderImpl
import com.moscow.preference.ThemeProviderImpl
import com.moscow.repository.ActorRepositoryImpl
import com.moscow.repository.CollectionsRepositoryImpl
import com.moscow.repository.GenreRepositoryImpl
import com.moscow.repository.HomeRepositoryImpl
import com.moscow.repository.MovieRepositoryImpl
import com.moscow.repository.SearchRepositoryImpl
import com.moscow.repository.SeriesRepositoryImpl
import com.moscow.repository.login.LoginRepositoryImpl
import com.moscow.repository.preference.PreferenceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindSeriesRepository(impl: SeriesRepositoryImpl): SeriesRepository

    @Binds
    @Singleton
    abstract fun bindGenreRepository(impl: GenreRepositoryImpl): GenreRepository

    @Binds
    @Singleton
    abstract fun bindActorRepository(impl: ActorRepositoryImpl): ActorRepository

    @Binds
    @Singleton
    abstract fun bindCollectionsRepository(impl: CollectionsRepositoryImpl): CollectionsRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(impl: PreferenceRepositoryImpl): PreferenceRepository


    @Binds
    @Singleton
    abstract fun bindLanguageProvider(impl: LanguageProviderImpl): LanguageProvider

    @Binds
    @Singleton
    abstract fun bindThemeProvider(impl: ThemeProviderImpl): ThemeProvider

    @Binds
    @Singleton
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

}