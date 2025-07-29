package com.moscow.di

import com.moscow.DeviceLanguageProvider
import com.moscow.data_source.system.LanguageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemModule {

    @Provides
    @Singleton
    fun provideLanguageProvider(): LanguageProvider {
        return DeviceLanguageProvider()
    }
}