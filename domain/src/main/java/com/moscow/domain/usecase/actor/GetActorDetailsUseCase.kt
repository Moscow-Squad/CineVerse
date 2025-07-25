package com.moscow.domain.usecase.actor

import com.moscow.domain.model.ActorDetails
import com.moscow.domain.repository.ActorRepository

class GetActorDetailsUseCase(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): ActorDetails =
        actorRepository.getActorDetails(actorId)
}
