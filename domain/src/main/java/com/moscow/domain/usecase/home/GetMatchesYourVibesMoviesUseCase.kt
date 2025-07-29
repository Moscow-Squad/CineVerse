package com.moscow.domain.usecase.home

import com.moscow.domain.repository.HomeRepository
import com.moscow.domain.model.Movie
import javax.inject.Inject

class GetMatchesYourVibesMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(genreId:Int, page:Int): List<Movie> =
        homeRepository.getMatchYourVibeMovies(genreId,page)
}