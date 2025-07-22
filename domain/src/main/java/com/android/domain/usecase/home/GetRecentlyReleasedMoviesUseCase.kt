package com.android.domain.usecase.home

import com.android.domain.repository.HomeRepository

class GetRecentlyReleasedMoviesUseCase (
    private val repository: HomeRepository
){
    suspend fun getRecentlyReleasedMovies() = repository.getRecentlyReleasedMovies()
}