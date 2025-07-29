package com.moscow.domain.usecase.actor

import com.moscow.domain.model.ActorDetails
import com.moscow.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): ActorDetails =
        actorRepository.getActorDetails(actorId)
}
