package com.data_source.remote

import com.remote.dto.actor.ActorBestOfMoviesDto
import com.remote.dto.actor.ActorImagesResponse
import com.remote.dto.actor.ActorSocialMediaDto
import com.remote.dto.details.ActorDetailsDto

interface ActorDetailsRemoteDataSource {
    suspend fun getActorDetails(actorId: Int): ActorDetailsDto
    suspend fun getGallery(actorId: Int): ActorImagesResponse
    suspend fun getBestOfMovies(actorId: Int): ActorBestOfMoviesDto
    suspend fun getSocialMedia(actorId: Int): ActorSocialMediaDto
}