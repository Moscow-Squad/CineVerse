package com.android.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Movie

class GetMatchesYourVibesMoviesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(genreId:Int, page:Int): List<Movie> =
        homeRepository.getMatchYourVibeMovies(genreId,page)
}