package com.remote

import android.util.Log
import com.dto.SuggestionResponse
import io.ktor.client.request.get
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter

class ExploreApiService(
    private val client: HttpClient
){

}

