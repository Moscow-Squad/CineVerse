package com.di

import com.remote.ExploreRemoteDataSource
import com.remote.actordetails.ActorDetailsRemoteDataSource
import com.remote.source.SearchRemoteDataSource
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val remoteSourceModule = module {
    single{ HttpClientFactory.create(OkHttp.create()) }
    single{ SearchRemoteDataSource(get()) }
    single{ ExploreRemoteDataSource(get()) }
    single{ ActorDetailsRemoteDataSource(get()) }
}