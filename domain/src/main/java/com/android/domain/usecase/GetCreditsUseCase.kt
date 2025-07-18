package com.android.domain.usecase

import com.android.domain.model.CreditsDetails
import com.android.domain.repository.DetailsRepository

class GetCreditsUseCase(
    private val detailsRepository: DetailsRepository
) {

    suspend operator fun invoke(id:Int): CreditsDetails =
       detailsRepository.getCreditsDetails(id)
}