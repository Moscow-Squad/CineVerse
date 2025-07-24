package com.remote.data_source

import com.data_source.remote.ActorRemoteDataSource
import com.remote.dto.actor.ActorBestOfMoviesDto
import com.remote.dto.actor.ActorImagesDto
import com.remote.dto.actor.ActorSocialMediaDto
import com.remote.dto.details.ActorDetailsDto
import com.remote.services.ActorService
import com.utils.handleApi


class ActorRemoteDataSourceImpl(private val actorService: ActorService) :
    ActorRemoteDataSource {
    override suspend fun getActorDetails(id: Int): ActorDetailsDto =
        handleApi {
            actorService.getActorDetails(id)
        }


    override suspend fun getActorGallery(id: Int): ActorImagesDto =
        handleApi {
            actorService.getActorGallery(id)
        }

    override suspend fun getActorBestMovies(id: Int): ActorBestOfMoviesDto =
        handleApi {
            actorService.getActorBestMovies(id)
        }

    override suspend fun getActorSocialMedia(id: Int): ActorSocialMediaDto =
        handleApi {
            actorService.getActorSocialMedia(id)
        }
}