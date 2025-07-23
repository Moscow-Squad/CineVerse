package com.di

import com.data_source.remote.ActorRemoteDataSource
import com.data_source.remote.CollectionRemoteDataSource
import com.data_source.remote.GenreRemoteDataSource
import com.data_source.remote.MovieRemoteDataSource
import com.data_source.remote.SearchRemoteDataSource
import com.data_source.remote.SeriesRemoteDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.remote.interceptors.CineverseInterceptor
import com.remote.services.*
import com.remote.data_source.*
import com.utils.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 20L
private val contentType = "application/json".toMediaType()

inline fun <reified T> Module.bindService() {
    single { get<Retrofit>().create(T::class.java) }
}

val dataSourceModule = module {

    // Networking
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    singleOf(::CineverseInterceptor)

    single {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<CineverseInterceptor>())
            .build()
    }

    // Serialization
    single {
        Json { ignoreUnknownKeys = true }
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .client(get())
            .build()
    }

    // Retrofit Services
    bindService<ActorService>()
    bindService<GenreService>()
    bindService<CollectionsService>()
    bindService<MovieService>()
    bindService<SeriesService>()
    bindService<SearchService>()

    // Remote Data Sources
    singleOf(::SearchRemoteDataSourceImpl) bind SearchRemoteDataSource::class
    singleOf(::ActorRemoteDataSourceImpl) bind ActorRemoteDataSource::class
    singleOf(::MovieRemoteDataSourceImpl) bind MovieRemoteDataSource::class
    singleOf(::CollectionRemoteRemoteDataSourceImpl) bind CollectionRemoteDataSource::class
    singleOf(::SeriesRemoteDataSourceImpl) bind SeriesRemoteDataSource::class
    singleOf(::GenreRemoteDataSourceImpl) bind GenreRemoteDataSource::class
}
