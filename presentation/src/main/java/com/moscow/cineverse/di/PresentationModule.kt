package com.moscow.cineverse.di

import com.android.domain.usecase.SearchUseCase
import com.moscow.cineverse.screen.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val viewModels = module {
    viewModelOf(::ExploreViewModel)
}
val useCases = module {
    single {
        SearchUseCase(get())
    }
}

val presentationModule = viewModels + useCases
