package com.moscow.domain.usecase.movie

import com.moscow.domain.model.Movie
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(page: Int): List<RatedMovieResult> {
        val user = preferenceRepository.getUser()
        val userid = if (user is UserType.AuthenticatedUser) user.id else "0"
        return movieRepository.getRatedMovies(userid.toInt(), page)
    }

    data class RatedMovieResult(
        val movie: Movie,
        val rating: Float
    )
}