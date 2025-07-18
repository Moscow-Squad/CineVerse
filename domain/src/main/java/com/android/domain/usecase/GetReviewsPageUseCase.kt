package com.android.domain.usecase

import com.android.domain.model.Review
import com.android.domain.repository.DetailsRepository
import com.android.domain.repository.ReviewsRepository
import kotlinx.coroutines.flow.Flow

class GetReviewsPageUseCase(private val repository: ReviewsRepository) {
    suspend operator fun invoke(movieId:Int, page: Int, isMovie: Boolean): List<Review> =
         repository.getReviewsPage(movieId, page, isMovie)

}
