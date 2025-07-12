package com.moscow.cineverse.di

import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.GetMovieByGenreIdUseCase
import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.android.domain.usecase.SearchUseCase
import com.android.domain.usecase.SuggestionUseCase
import org.koin.dsl.module

val useCases = module {
    single { GetLocalSuggestions(get()) }
    single { SuggestionUseCase(get()) }
    single { SearchUseCase(get()) }
    single { GenreUseCase(get()) }
    single { GetMoviesUseCase(get()) }
    single { GetSeriesUseCase(get()) }
    single { GetMovieByGenreIdUseCase(get()) }
    single { GetSeriesByGenreIdUseCase(get()) }
}

val presentationModule = viewModels + useCases
