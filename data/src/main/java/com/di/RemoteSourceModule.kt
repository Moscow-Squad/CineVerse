package com.di

import com.remote.source.ActorDetailsRemoteDataSource
import com.remote.source.CollectionsDataSource
import com.remote.source.DetailsRemoteDataSource
import com.remote.source.ExploreRemoteDataSource
import com.remote.source.RecommendationsMoviesRemoteDataSource
import com.remote.source.ReviewsRemoteDataSource
import com.remote.source.SearchRemoteDataSource
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val remoteSourceModule = module {

    single{ HttpClientFactory.create(OkHttp.create()) }
    single{ SearchRemoteDataSource(get()) }
    single{ ExploreRemoteDataSource(get()) }
    single{ ActorDetailsRemoteDataSource(get()) }
    single{ DetailsRemoteDataSource(get()) }
    single { HttpClientFactory.create(OkHttp.create()) }
    single { SearchRemoteDataSource(get()) }
    single { ExploreRemoteDataSource(get()) }
    single { ActorDetailsRemoteDataSource(get()) }
    single { DetailsRemoteDataSource(get()) }
    single { CollectionsDataSource(get()) }
    single { ReviewsRemoteDataSource(get()) }
    single { RecommendationsMoviesRemoteDataSource(get()) }

}