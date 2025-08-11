package com.moscow.remote.services

import com.moscow.remote.dto.actor.ActorBestOfMoviesDto
import com.moscow.remote.dto.actor.ActorImagesDto
import com.moscow.remote.dto.actor.ActorSocialMediaDto
import com.moscow.remote.dto.details.ActorDetailsDto
import com.moscow.utils.ACTOR
import com.moscow.utils.EXTERNAL_IDS
import com.moscow.utils.IMAGES
import com.moscow.utils.MOVIE_CREDITS
import retrofit2.http.*
import retrofit2.Response

interface ActorService {

    @GET("$ACTOR{actorId}")
    suspend fun getActorDetails(
        @Path("actorId") id: Int
    ): Response<ActorDetailsDto>

    @GET("$ACTOR{actorId}$IMAGES")
    suspend fun getActorGallery(
        @Path("actorId") id: Int
    ): Response<ActorImagesDto>

    @GET("$ACTOR{actorId}$MOVIE_CREDITS")
    suspend fun getActorBestMovies(
        @Path("actorId") id: Int
    ): Response<ActorBestOfMoviesDto>

    @GET("$ACTOR{actorId}$EXTERNAL_IDS")
    suspend fun getActorSocialMedia(
        @Path("actorId") id: Int
    ): Response<ActorSocialMediaDto>
}
