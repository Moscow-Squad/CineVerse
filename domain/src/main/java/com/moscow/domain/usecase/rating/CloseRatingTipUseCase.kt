package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.PreferenceRepository
import javax.inject.Inject

class CloseRatingTipUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke() = preferenceRepository.closeRatingTip()
}