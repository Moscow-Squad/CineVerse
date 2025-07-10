package com.repository

import com.android.domain.repository.MovieRepository
import com.utils.BaseRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl :MovieRepository, BaseRepository() {
    override fun getLocalSuggestions(): Flow<List<String>> {
        TODO("Not yet implemented")
    }
}