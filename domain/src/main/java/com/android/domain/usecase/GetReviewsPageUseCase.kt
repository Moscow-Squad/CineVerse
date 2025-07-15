package com.android.domain.usecase

import com.android.domain.model.Review
import com.android.domain.repository.DetailsRepository

class GetReviewsPageUseCase(private val repository: DetailsRepository) {
    suspend operator fun invoke(movieId:Int, page: Int, isMovie: Boolean): List<Review> {
        return repository.getReviewsPage(movieId, page, isMovie)
    }
}
