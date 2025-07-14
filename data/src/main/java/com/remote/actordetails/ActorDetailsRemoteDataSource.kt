package com.remote.actordetails

import com.remote.dto.ActorBestOfMoviesDto
import com.remote.dto.ActorDetailsDto
import com.remote.dto.ActorImagesDto
import com.remote.dto.ActorImagesResponse
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

    suspend fun getGallery(actorId: Int): List<ActorImagesDto>{
        return client.performCall<Unit, ActorImagesResponse>(
            method = HttpMethod.Get,
            path = "$ACTOR/{$actorId}/images",
        ).profiles
    }

    suspend fun getBestOfMovies(actorId: Int): ActorBestOfMoviesDto{
        return client.performCall<Unit, ActorBestOfMoviesDto>(
            method = HttpMethod.Get,
            path = "$ACTOR/{$actorId}/movie_credits",
        )
    }
}