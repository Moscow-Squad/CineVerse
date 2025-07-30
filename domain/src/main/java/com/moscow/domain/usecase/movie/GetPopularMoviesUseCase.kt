package com.moscow.domain.usecase.movie

import com.moscow.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(page: Int) = repository.getPopularMovies(page).distinctBy { it.id }
}