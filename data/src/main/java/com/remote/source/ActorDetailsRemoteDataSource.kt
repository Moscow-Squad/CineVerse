package com.remote.source

import com.remote.dto.ActorBestOfMoviesDto
import com.remote.dto.ActorImagesDto
import com.remote.dto.ActorImagesResponse
import com.remote.dto.ActorSocialMediaDto
import com.remote.dto.details.ActorDetailsDto
import com.utils.ACTOR
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class ActorDetailsRemoteDataSource( private val client: HttpClient) {
    suspend fun getActorDetails(actorId: Int): ActorDetailsDto {
        return client.performCall<Unit, ActorDetailsDto>(
            method = HttpMethod.Get,
            path = "${ACTOR}$actorId",
        )
    }

    suspend fun getGallery(actorId: Int): List<ActorImagesDto>{
        return client.performCall<Unit, ActorImagesResponse>(
            method = HttpMethod.Get,
            path = "${ACTOR}$actorId/images",
        ).profiles
    }

    suspend fun getBestOfMovies(actorId: Int): ActorBestOfMoviesDto {
        return client.performCall<Unit, ActorBestOfMoviesDto>(
            method = HttpMethod.Get,
            path = "${ACTOR}$actorId/movie_credits",
        )
    }

    suspend fun getSocialMedia(actorId: Int): ActorSocialMediaDto {
        return client.performCall<Unit, ActorSocialMediaDto>(
            method = HttpMethod.Get,
            path = "${ACTOR}$actorId/external_ids",
        )
    }
}