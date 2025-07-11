package com.android.domain.repository

import com.android.domain.model.Suggestion
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getRemoteSuggestions(keyWord:String,page:Int):Flow<List<Suggestion>>
    fun getLocalSuggestions(keyWord:String):Flow<List<Suggestion>>

}