package com.repository

import com.android.domain.model.Suggestion
import com.android.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl: SearchRepository{

    override fun getSuggestions(keyWord: String): Flow<List<Suggestion>> = flow {
        emit(emptyList())
    }
}