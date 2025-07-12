package com.di

import androidx.room.Room
import com.local.SearchLocalDateSourceImpl
import com.local.dao.search.ActorDao
import com.local.dao.search.MovieDao
import com.local.dao.search.SearchHistoryDao
import com.local.dao.search.SeriesDao
import com.local.database.CineVerseDataBase
import com.repository.explore.search.SearchLocalDateSource
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
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single<MovieDao> { get<CineVerseDataBase>().movieDao() }
    single<SearchHistoryDao> { get<CineVerseDataBase>().searchHistoryDao() }
    single<ActorDao> { get<CineVerseDataBase>().actorDao() }
    single<SeriesDao> { get<CineVerseDataBase>().seriesDao() }

    singleOf(::SearchLocalDateSourceImpl) bind SearchLocalDateSource::class
}

