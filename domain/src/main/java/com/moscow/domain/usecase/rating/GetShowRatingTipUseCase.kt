package com.moscow.domain.usecase.rating

import com.moscow.domain.repository.RatingTipsRepository
import javax.inject.Inject

class GetShowRatingTipUseCase @Inject constructor(
    private val ratingTipsRepository: RatingTipsRepository
) {
    suspend operator fun invoke() = ratingTipsRepository.showRatingTip()
}