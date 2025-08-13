package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetShowRatingTipUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke() = preferenceRepository.showRatingTip()
}