package com.moscow.cineverse.di

import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.SuggestionUseCase
import org.koin.dsl.module
import com.android.domain.usecase.SearchUseCase

val useCases = module {
    single { GetLocalSuggestions(get()) }
    single { SuggestionUseCase(get()) }
    single { SearchUseCase(get()) }
    single { GenreUseCase(get()) }
}

val presentationModule = viewModels + useCases
