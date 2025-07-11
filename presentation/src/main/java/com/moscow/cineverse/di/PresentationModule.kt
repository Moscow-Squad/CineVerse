package com.moscow.cineverse.di

import com.android.domain.usecase.SuggestionUseCase
import com.moscow.cineverse.screen.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.android.domain.usecase.SearchUseCase


val viewModels = module {
    viewModelOf(::ExploreViewModel)
}

val useCases = module {
    single {
        SuggestionUseCase(get())
    }
    single {
        SearchUseCase(get())
    }
}


val presentationModule = viewModels + useCases
