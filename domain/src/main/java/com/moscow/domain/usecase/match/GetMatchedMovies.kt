package com.moscow.domain.usecase.match

import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetMatchedMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        page: Int,
        genres: String?,
        runtimeGte: Int?,
        runtimeLte: Int?,
        releaseDateGte: String?,
        releaseDateLte: String?
    ) = movieRepository.getMatchedMovies(
        page = page,
        genres = genres,
        runtimeGte = runtimeGte,
        runtimeLte = runtimeLte,
        releaseDateGte = releaseDateGte,
        releaseDateLte = releaseDateLte,
    )
}