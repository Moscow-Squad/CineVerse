package com.android.domain.repository
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getLocalSuggestions() : Flow<List<String>>
}