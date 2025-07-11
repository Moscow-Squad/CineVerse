package com.di

import androidx.room.Room
import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.database.CineVerseDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.local.SearchLocalDateSourceImpl
import com.repository.search.SearchLocalDateSource

private const val CINE_VERSE_DATABASE = "cineverse_database"

val localServiceModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CineVerseDataBase::class.java,
            CINE_VERSE_DATABASE
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single<MovieDao> { get<CineVerseDataBase>().movieDao() }
    single<SearchHistoryDao> { get<CineVerseDataBase>().searchHistoryDao() }


    singleOf(::SearchLocalDateSourceImpl) bind SearchLocalDateSource::class

}
