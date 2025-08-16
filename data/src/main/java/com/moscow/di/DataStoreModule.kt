package com.moscow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    @Named("onboarding")
    fun provideOnboardingDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.onboardingDataStore

    @Provides
    @Singleton
    @Named("user_settings")
    fun provideUserSettingsDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userDataStore

}

private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "onboarding_preferences"
)

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_settings_preferences"
)
