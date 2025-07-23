package com.di

import androidx.room.Room
import com.local.DetailsLocalDataSourceImpl
import com.local.SearchLocalDataSourceImpl
import com.local.database.CineVerseDataBase
import com.data_source.local.DetailsLocalDataSource
import com.data_source.local.SearchLocalDataSource
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
}

