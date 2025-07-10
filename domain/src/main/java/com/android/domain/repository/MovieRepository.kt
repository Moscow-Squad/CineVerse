package com.android.domain.repository

interface MovieRepository {
    fun getLocalSuggestions() : List<String>
}