package com.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true },
                    contentType =  ContentType.Application.Json
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMzdkOWY1Njg2ODVlZmFkYWZhMmNlMGE4NzE1OTdmYiIsIm5iZiI6MTc1MTQ3ODAxMi40MTgsInN1YiI6IjY4NjU2ZWZjMzVmNTZjNzQ4NzZhYTM0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.m1SEYwrplAih5yTaMhekrgBwD8ZkL9FCdFO9OmXHc_I"
                )
            }
        }
    }
}