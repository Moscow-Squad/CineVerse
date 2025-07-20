package com.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.remote.interceptors.CineverseInterceptor
import com.remote.source.ActorDetailsRemoteDataSource
import com.remote.source.CollectionsDataSource
import com.remote.source.DetailsRemoteDataSource
import com.remote.source.ExploreRemoteDataSource
import com.remote.source.RecommendationsMoviesRemoteDataSource
import com.remote.source.ReviewsRemoteDataSource
import com.remote.source.SearchRemoteDataSource
import com.utils.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 20L
private val contentType = "application/json".toMediaType()

val dataSourceModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

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

    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create<>()
    }

    singleOf(::SearchRemoteDataSource)
    singleOf(::ExploreRemoteDataSource)
    singleOf(::ActorDetailsRemoteDataSource)
    singleOf(::DetailsRemoteDataSource)
    singleOf(::CollectionsDataSource)
    singleOf(::ReviewsRemoteDataSource)
    singleOf(::RecommendationsMoviesRemoteDataSource)
}