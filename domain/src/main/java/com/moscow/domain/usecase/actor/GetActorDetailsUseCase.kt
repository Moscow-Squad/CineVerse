package com.moscow.domain.usecase.actor

import com.moscow.domain.model.Actor
import com.moscow.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): Actor =
        actorRepository.getActorDetails(actorId)
}
