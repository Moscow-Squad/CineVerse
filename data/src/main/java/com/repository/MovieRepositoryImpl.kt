package com.repository

import com.android.domain.repository.MovieRepository
import com.utils.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MovieRepositoryImpl :MovieRepository, BaseRepository() {
    override fun getLocalSuggestions(): Flow<List<String>> {
       return flowOf(listOf("Batman", "Dark night", "cooper", "iron man", "baby day's out"))
    }
}