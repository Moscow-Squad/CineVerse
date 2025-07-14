package com.remote.actordetails

import com.remote.dto.ActorDetailsDto
import com.utils.ACTOR
import com.utils.PERSONID
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

class ActorDetailsRemoteDataSource( private val client: HttpClient ) {
    suspend fun getActorDetails(actorId: Int): ActorDetailsDto{
        return client.performCall<Unit, ActorDetailsDto>(
            method = HttpMethod.Get,
            path = ACTOR,
            requestBuilder = {
                parameter(PERSONID, actorId)
            }
        )
    }
}