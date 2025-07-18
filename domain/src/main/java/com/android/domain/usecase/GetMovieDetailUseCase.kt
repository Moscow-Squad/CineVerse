package com.android.domain.usecase

import com.android.domain.model.details.MovieDetail
import com.android.domain.repository.DetailsRepository

class GetMovieDetailUseCase(
    private val repository: DetailsRepository,
) {
    suspend operator fun invoke(id: Int): MovieDetail {
        return repository.getMoviesDetail(id)
    }
}