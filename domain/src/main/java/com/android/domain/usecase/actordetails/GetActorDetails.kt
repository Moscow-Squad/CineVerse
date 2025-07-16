package com.android.domain.usecase.actordetails

import com.android.domain.model.ActorDetails
import com.android.domain.repository.ActorDetailsRepository

class GetActorDetails(private val actorDetailsRepository: ActorDetailsRepository) {
    suspend fun getActorDetails(actorId: Int) : ActorDetails{
        return actorDetailsRepository.getActorDetails(actorId)
    }
}