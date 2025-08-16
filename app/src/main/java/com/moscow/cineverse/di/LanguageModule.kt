package com.moscow.cineverse.di

import com.moscow.domain.service.language.LanguageProvider
import com.moscow.repository.language.LanguageManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LanguageModule {

    @Binds
    @Singleton
    abstract fun bindLanguageProvider(languageImpl: LanguageManagerImpl): LanguageProvider
}