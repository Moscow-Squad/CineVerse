package com.remote.services

import retrofit2.http.*
import com.remote.dto.actor.ActorBestOfMoviesDto
import com.remote.dto.actor.ActorImagesDto
import com.remote.dto.actor.ActorSocialMediaDto
import com.remote.dto.details.*
import com.utils.ACTOR
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