package com.moscow.di

import com.moscow.data_source.remote.HomeRemoteDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.moscow.data_source.remote.ActorRemoteDataSource
import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.data_source.remote.LoginRemoteDataSource
import com.moscow.data_source.remote.MovieRemoteDataSource
import com.moscow.data_source.remote.SearchRemoteDataSource
import com.moscow.data_source.remote.SeriesRemoteDataSource
import com.moscow.data_source.system.LanguageProvider
import com.moscow.remote.data_source.ActorRemoteDataSourceImpl
import com.moscow.remote.data_source.CollectionRemoteDataSourceImpl
import com.moscow.remote.data_source.GenreRemoteDataSourceImpl
import com.moscow.remote.data_source.LoginRemoteDataSourceImpl
import com.moscow.remote.data_source.MovieRemoteDataSourceImpl
import com.moscow.remote.data_source.SearchRemoteDataSourceImpl
import com.moscow.remote.data_source.SeriesRemoteDataSourceImpl
import com.moscow.remote.interceptors.CineVerseInterceptor
import com.moscow.remote.services.ActorService
import com.moscow.remote.services.CollectionsService
import com.moscow.remote.services.GenreService
import com.moscow.remote.services.LoginService
import com.moscow.remote.services.MovieService
import com.moscow.remote.services.SearchService
import com.moscow.remote.services.SeriesService
import com.moscow.utils.BASE_URL
import com.moscow.remote.data_source.HomeRemoteDataSourceImpl
import com.remote.services.HomeService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 20L
private val contentType = "application/json".toMediaType()

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindActorRemoteDataSource(impl: ActorRemoteDataSourceImpl): ActorRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(impl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCollectionRemoteDataSource(impl: CollectionRemoteDataSourceImpl): CollectionRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSeriesRemoteDataSource(impl: SeriesRemoteDataSourceImpl): SeriesRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGenreRemoteDataSource(impl: GenreRemoteDataSourceImpl): GenreRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLoginRemoteDataSource(impl: LoginRemoteDataSourceImpl): LoginRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindHomeRemoteDataSource(impl: HomeRemoteDataSourceImpl): HomeRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        }

        @Provides
        @Singleton
        fun provideCineVerseInterceptor(languageProvider: LanguageProvider): CineVerseInterceptor {
            return CineVerseInterceptor(languageProvider)
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            cineVerseInterceptor: CineVerseInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cineVerseInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json { ignoreUnknownKeys = true }
        }

        @Provides
        @Singleton
        fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        fun provideActorService(retrofit: Retrofit): ActorService {
            return retrofit.create(ActorService::class.java)
        }

        @Provides
        @Singleton
        fun provideGenreService(retrofit: Retrofit): GenreService {
            return retrofit.create(GenreService::class.java)
        }

        @Provides
        @Singleton
        fun provideCollectionsService(retrofit: Retrofit): CollectionsService {
            return retrofit.create(CollectionsService::class.java)
        }

        @Provides
        @Singleton
        fun provideMovieService(retrofit: Retrofit): MovieService {
            return retrofit.create(MovieService::class.java)
        }

        @Provides
        @Singleton
        fun provideSeriesService(retrofit: Retrofit): SeriesService {
            return retrofit.create(SeriesService::class.java)
        }

        @Provides
        @Singleton
        fun provideSearchService(retrofit: Retrofit): SearchService {
            return retrofit.create(SearchService::class.java)
        }

        @Provides
        @Singleton
        fun provideLoginService(retrofit: Retrofit): LoginService {
            return retrofit.create(LoginService::class.java)
        }

        @Provides
        @Singleton
        fun provideHomeService(retrofit: Retrofit): HomeService {
            return retrofit.create(HomeService::class.java)
        }
    }
}

