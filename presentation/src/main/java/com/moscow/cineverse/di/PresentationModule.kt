package com.moscow.cineverse.di

import com.moscow.cineverse.screen.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val presentationModule = module {
    viewModelOf(::ExploreViewModel)
}