package com.moscow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.local.HomeLocalDataSource
import com.moscow.data_source.local.RecentlyViewedLocalDataSource
import com.moscow.data_source.local.SearchLocalDataSource
import com.moscow.local.data_source.DetailsLocalDataSourceImpl
import com.moscow.local.data_source.HomeLocalDataSourceImpl
import com.moscow.local.data_source.RecentlyViewedLocalDataSourceImpl
import com.moscow.local.data_source.SearchLocalDataSourceImpl
import com.moscow.local.database.CineVerseDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val CINE_VERSE_DATABASE = "cineverse_database"

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchLocalDataSource(impl: SearchLocalDataSourceImpl): SearchLocalDataSource

    @Binds
    @Singleton
    abstract fun bindDetailsLocalDataSource(impl: DetailsLocalDataSourceImpl): DetailsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindHomeLocalDataSource(impl: HomeLocalDataSourceImpl): HomeLocalDataSource

    @Binds
    @Singleton
    abstract fun bindRecentlyViewedLocalDataSource(impl: RecentlyViewedLocalDataSourceImpl): RecentlyViewedLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideCineVerseDataBase(@ApplicationContext context: Context): CineVerseDataBase {
            return Room.databaseBuilder(
                context,
                CineVerseDataBase::class.java,
                CINE_VERSE_DATABASE
            ).fallbackToDestructiveMigration(true).build()
        }

        @Provides
        @Singleton
        fun provideMovieDao(database: CineVerseDataBase) = database.movieDao()

        @Provides
        @Singleton
        fun provideSearchHistoryDao(database: CineVerseDataBase) = database.searchHistoryDao()

        @Provides
        @Singleton
        fun provideActorDao(database: CineVerseDataBase) = database.actorDao()

        @Provides
        @Singleton
        fun provideSeriesDao(database: CineVerseDataBase) = database.seriesDao()

        @Provides
        @Singleton
        fun provideFavouriteGenreDao(database: CineVerseDataBase) = database.favouriteGenreDao()

        @Provides
        @Singleton
        fun provideHomeCacheDao(database: CineVerseDataBase) = database.homeCacheDao()

        @Provides
        @Singleton
        fun provideRecentlyViewedDao(database: CineVerseDataBase) = database.recentlyViewedDao()

        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile("cineverse_preferences") }
            )
        }
    }
}