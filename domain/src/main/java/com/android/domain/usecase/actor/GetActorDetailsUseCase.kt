package com.android.domain.usecase.actor

import com.android.domain.model.ActorDetails
import com.android.domain.repository.ActorRepository

class GetActorDetailsUseCase(private val actorRepository: ActorRepository) {
    suspend operator fun invoke(actorId: Int): ActorDetails =
        actorRepository.getActorDetails(actorId)
}
