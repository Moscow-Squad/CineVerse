package com.di

import com.remote.ExploreApiService
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val apiServiceModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    single { ExploreApiService(get()) }
}
