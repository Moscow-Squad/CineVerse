package com.remote.data_source

import com.data_source.remote.ActorDetailsRemoteDataSource
import com.remote.dto.actor.ActorBestOfMoviesDto
import com.remote.dto.actor.ActorImagesDto
import com.remote.dto.actor.ActorSocialMediaDto
import com.remote.dto.details.ActorDetailsDto
import com.remote.services.ActorDetailsService
import com.utils.handleApi


class ActorDetailsRemoteDataSourceImpl(private val actorDetailsService: ActorDetailsService) :
    ActorDetailsRemoteDataSource {
    override suspend fun getActorDetails(actorId: Int): ActorDetailsDto = handleApi {
        actorDetailsService.getActorDetails(actorId)
    }


    override suspend fun getGallery(actorId: Int): ActorImagesDto = handleApi {
        actorDetailsService.getGallery(actorId)
    }

    override suspend fun getBestOfMovies(actorId: Int): ActorBestOfMoviesDto = handleApi {
        actorDetailsService.getActorBestMovies(actorId)
    }

    override suspend fun getSocialMedia(actorId: Int): ActorSocialMediaDto = handleApi {
        actorDetailsService.getSocialMedia(actorId)
    }
}