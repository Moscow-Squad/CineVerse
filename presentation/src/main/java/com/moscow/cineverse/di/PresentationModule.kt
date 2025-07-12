package com.moscow.cineverse.di

import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.moscow.cineverse.screen.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
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
    single { GetMoviesUseCase(get()) }
    single { GetSeriesUseCase(get()) }
}

val presentationModule = viewModels + useCases
