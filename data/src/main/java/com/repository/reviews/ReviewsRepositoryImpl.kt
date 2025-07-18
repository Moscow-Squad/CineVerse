package com.repository.reviews

import com.android.domain.model.Movie
import com.android.domain.model.Review
import com.android.domain.repository.RecommendationsMoviesRepository
import com.android.domain.repository.ReviewsRepository
import com.mapper.toDomain
import com.remote.source.RecommendationsMoviesRemoteDataSource
import com.remote.source.ReviewsRemoteDataSource
import com.repository.mapper.toDomain
import com.utils.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReviewsRepositoryImpl(
private val reviewsRemoteDataSource: ReviewsRemoteDataSource,

) : ReviewsRepository , BaseRepository(){
    override suspend fun getReviewsPage(id:Int, page: Int, isMovie: Boolean):List<Review> =
        tryToExecute {

            val reviews = reviewsRemoteDataSource.getReviews(id, page, isMovie)
            if(reviews.results != null)
                reviews.results.mapNotNull { runCatching { it?.toDomain() }.getOrNull() }
            else
                emptyList()
        }
}