package com.moscow.remote.services

import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.actor.ActorSocialMediaDto
import com.moscow.remote.dto.details.ActorDetailsDto
import com.moscow.utils.ACTOR
import retrofit2.http.*
import retrofit2.Response

interface ActorService {
    @GET("$ACTOR{actorId}")
    suspend fun getActorDetails(
        @Path("actorId") id: Int
    ): Response<ActorDetailsDto>

    @GET("$ACTOR{actorId}/images")
    suspend fun getActorGallery(
        @Path("actorId") id: Int
    ): Response<ActorImagesDto>

    @GET("$ACTOR{actorId}/movie_credits")
    suspend fun getActorBestMovies(
        @Path("actorId") id: Int
    ): Response<ActorBestOfMoviesDto>

    @GET("$ACTOR{actorId}/external_ids")
    suspend fun getActorSocialMedia(
        @Path("actorId") id: Int
    ): Response<ActorSocialMediaDto>
}
