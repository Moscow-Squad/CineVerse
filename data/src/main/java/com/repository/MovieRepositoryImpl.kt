package com.repository

import com.android.domain.repository.MovieRepository
import com.utils.BaseRepository

class MovieRepositoryImpl :MovieRepository, BaseRepository() {
    override fun getLocalSuggestions(): List<String> {
        TODO("Not yet implemented")
    }
}