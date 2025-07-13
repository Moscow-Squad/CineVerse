package com.utils

import androidx.compose.ui.text.intl.Locale
import com.moscow.cineverse.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

@SuppressWarnings("kotlin:S6312")
suspend inline fun <reified Request, reified Response> HttpClient.performCall(
    method: HttpMethod,
    baseUrl: String = BASE_URL,
    path: String,
    body: Request? = null,
    contentType: ContentType = ContentType.Application.Json,
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = { },
): Response {
    val response =
        request {
            configureRequestDefaults(
                method = method,
                baseUrl = baseUrl,
                path = path,
                requestBuilder = requestBuilder,
            )
            body?.let { setBody(body) }
        }
    return response.body<Response>()
}

suspend fun HttpRequestBuilder.configureRequestDefaults(
    method: HttpMethod,
    baseUrl: String,
    path: String,
    requestBuilder: HttpRequestBuilder.() -> Unit,
) {
    this.method = method
    url(baseUrl.plus(path))
    addDefaultsHeaders()
    addDefaultParameters()
    requestBuilder()
}

private suspend fun HttpRequestBuilder.addDefaultsHeaders() {
    headers {
        header(key = HttpHeaders.AcceptLanguage, Locale.current.language)
    }
}

private suspend fun HttpRequestBuilder.addDefaultParameters() {
    parameter(API_KEY, BuildConfig.TMDB_API_KEY)
}