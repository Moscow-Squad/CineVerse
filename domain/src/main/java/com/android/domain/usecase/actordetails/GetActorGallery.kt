package com.android.domain.usecase.actordetails

import com.android.domain.repository.ActorDetailsRepository

class GetActorGallery(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend fun getActorGallery(actorId: Int): List<String> {
        return actorDetailsRepository.getActorGallery(actorId)
    }
}