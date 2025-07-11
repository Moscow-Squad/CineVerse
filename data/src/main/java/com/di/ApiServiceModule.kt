package com.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val apiServiceModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }

}
