package com.android.domain.repository

import com.android.domain.model.Suggestion
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSuggestions(keyWord:String):Flow<List<Suggestion>>

}