package com.repository

import com.android.domain.repository.MovieRepository
import com.remote.source.SearchRemoteDataSource
import com.utils.BaseRepository

class MovieRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : MovieRepository, BaseRepository() {

}