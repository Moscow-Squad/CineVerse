package com.android.domain.usecase

import com.android.domain.model.Suggestion
import com.android.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SuggestionUseCase(
    private val searchRepository: SearchRepository,
) {
    fun getSuggestions(keyWord: String,page:Int): Flow<List<Suggestion>> = flow {
        val remoteSuggestions = searchRepository.getRemoteSuggestions(keyWord,page).first()
        //val localSuggestions = movieRepository.getLocalSuggestions().first()
        emit(remoteSuggestions)
    }

}