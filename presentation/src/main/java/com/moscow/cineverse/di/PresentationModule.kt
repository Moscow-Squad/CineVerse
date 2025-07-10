package com.moscow.cineverse.di

import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.moscow.cineverse.screen.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
val presentationModule = module {
    viewModelOf(::ExploreViewModel)
    factory { GetMoviesUseCase(get()) }
    factory { GetSeriesUseCase(get()) }
}