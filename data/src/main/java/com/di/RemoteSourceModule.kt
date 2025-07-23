package com.di

import com.data_source.remote.ActorDetailsRemoteDataSource
import com.data_source.remote.CollectionsDataSource
import com.data_source.remote.DetailsRemoteDataSource
import com.data_source.remote.ExploreRemoteDataSource
import com.data_source.remote.RecommendationsRemoteDataSource
import com.data_source.remote.ReviewsRemoteDataSource
import com.data_source.remote.SearchRemoteDataSource
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
import com.remote.source.ActorDetailsRemoteDataSource
import com.remote.source.CollectionsDataSource
import com.remote.source.DetailsRemoteDataSource
import com.remote.source.ExploreRemoteDataSource
import com.remote.source.RecommendationsMoviesRemoteDataSource
import com.remote.source.ReviewsRemoteDataSource
import com.remote.source.LoginRemoteDataSource
import com.remote.source.SearchRemoteDataSource
import io.ktor.client.engine.okhttp.OkHttp
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
    bindService<ActorDetailsService>()
    bindService<ExploreService>()
    bindService<CollectionsService>()
    bindService<DetailsService>()
    bindService<RecommendationsService>()
    bindService<ReviewsService>()
    bindService<SearchService>()

    // Remote Data Sources
    singleOf(::SearchRemoteDataSourceImpl) bind SearchRemoteDataSource::class
    singleOf(::ExploreRemoteDataSourceImpl) bind ExploreRemoteDataSource::class
    singleOf(::ActorDetailsRemoteDataSourceImpl) bind ActorDetailsRemoteDataSource::class
    singleOf(::DetailsRemoteDataSourceImpl) bind DetailsRemoteDataSource::class
    singleOf(::CollectionsDataSourceImpl) bind CollectionsDataSource::class
    singleOf(::ReviewsRemoteDataSourceImpl) bind ReviewsRemoteDataSource::class
    singleOf(::RecommendationsRemoteDataSourceImpl) bind RecommendationsRemoteDataSource::class
    single { LoginRemoteDataSource(get()) }
}
