package com.moscow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.moscow.data_source.local.DetailsLocalDataSource
import com.moscow.data_source.local.SearchLocalDataSource
import com.moscow.local.DetailsLocalDataSourceImpl
import com.moscow.local.SearchLocalDataSourceImpl
import com.moscow.local.database.CineVerseDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val CINE_VERSE_DATABASE = "cineverse_database"

val localSourceModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CineVerseDataBase::class.java,
            CINE_VERSE_DATABASE
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    singleOf(CineVerseDataBase::movieDao)
    singleOf(CineVerseDataBase::searchHistoryDao)
    singleOf(CineVerseDataBase::actorDao)
    singleOf(CineVerseDataBase::seriesDao)
    singleOf(CineVerseDataBase::favouriteGenreDao)
    singleOf(::SearchLocalDataSourceImpl) bind SearchLocalDataSource::class
    singleOf(::DetailsLocalDataSourceImpl) bind DetailsLocalDataSource::class

    single<Context> {
        androidApplication()
    }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                get<Context>().preferencesDataStoreFile("cineverse_preferences")
            }
        )
    }
}

