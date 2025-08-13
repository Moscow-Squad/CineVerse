package com.moscow.domain.usecase.rating

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

        val parseUserid = try {
            val equalIndex = userid.indexOfFirst { it == '=' }
            val commaIndex = userid.indexOfFirst { it == ',' }

            when {
                equalIndex == -1 || commaIndex == -1 || commaIndex <= equalIndex -> {
                    userid.toIntOrNull() ?: 0
                }

                else -> {
                    userid.substring(equalIndex + 1, commaIndex).toIntOrNull() ?: 0
                }
            }
        } catch (e: Exception) {
            0
        }

        return movieRepository.getRatedMovies(parseUserid, page)
    }

    data class RatedMovieResult(
        val movie: Movie,
        val rating: Float
    )
}