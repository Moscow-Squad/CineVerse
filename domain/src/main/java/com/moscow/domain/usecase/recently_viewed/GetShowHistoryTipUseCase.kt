package com.moscow.domain.usecase.recently_viewed

import com.moscow.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetShowHistoryTipUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke() = preferenceRepository.showHistoryTip()
}