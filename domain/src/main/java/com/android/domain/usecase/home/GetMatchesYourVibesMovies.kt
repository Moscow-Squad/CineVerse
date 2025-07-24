package com.android.domain.usecase.home

import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.HomeRepository

class GetMatchesYourVibesMovies(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(genreId:Int, page:Int): List<Movie> =
        homeRepository.getMatchYourVibeMovies(genreId,page)
}