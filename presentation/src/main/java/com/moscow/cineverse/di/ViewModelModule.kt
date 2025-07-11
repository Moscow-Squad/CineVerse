package com.moscow.cineverse.di

import com.moscow.cineverse.presentation.screens.search.SearchViewModel
import com.moscow.cineverse.screen.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module{
    viewModelOf(::ExploreViewModel)
    viewModelOf(::SearchViewModel)
}