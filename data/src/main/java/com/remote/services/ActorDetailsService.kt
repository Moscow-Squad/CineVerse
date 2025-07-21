package com.remote.services

import retrofit2.http.*
import com.remote.dto.*
import com.remote.dto.details.*
import com.utils.ACTOR
import retrofit2.Response

interface ActorDetailsService {

    @GET("$ACTOR{actorId}")
    suspend fun getActorDetails(
        @Path("actorId") actorId: Int
    ): Response<ActorDetailsDto>

    @GET("$ACTOR{actorId}/images")
    suspend fun getGallery(
        @Path("actorId") actorId: Int
    ): Response<ActorImagesResponse>

    @GET("$ACTOR{actorId}/movie_credits")
    suspend fun getBestOfMovies(
        @Path("actorId") actorId: Int
    ): Response<ActorBestOfMoviesDto>

    @GET("$ACTOR{actorId}/external_ids")
    suspend fun getSocialMedia(
        @Path("actorId") actorId: Int
    ): Response<ActorSocialMediaDto>
}