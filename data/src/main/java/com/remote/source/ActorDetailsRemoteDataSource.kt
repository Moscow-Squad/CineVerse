package com.remote.source

import com.remote.dto.ActorBestOfMoviesDto
import com.remote.dto.ActorImagesResponse
import com.remote.dto.ActorSocialMediaDto
import com.remote.dto.details.ActorDetailsDto
import com.remote.services.ActorDetailsService
import com.utils.handleApi


class ActorDetailsRemoteDataSource( private val actorDetailsService: ActorDetailsService) {
    suspend fun getActorDetails(actorId: Int): ActorDetailsDto = handleApi {
        actorDetailsService.getActorDetails(actorId)
    }


    suspend fun getGallery(actorId: Int): ActorImagesResponse = handleApi {
        actorDetailsService.getGallery(actorId)
    }

    suspend fun getBestOfMovies(actorId: Int): ActorBestOfMoviesDto = handleApi {
        actorDetailsService.getBestOfMovies(actorId)
    }

    suspend fun getSocialMedia(actorId: Int): ActorSocialMediaDto  = handleApi {
        actorDetailsService.getSocialMedia(actorId)
    }
}