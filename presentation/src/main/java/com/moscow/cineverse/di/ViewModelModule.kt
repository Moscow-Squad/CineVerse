package com.moscow.cineverse.di

import com.moscow.cineverse.screen.explore.ExploreViewModel
import com.moscow.cineverse.screen.movie_details.MovieDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModels = module{
    viewModelOf(::ExploreViewModel)
    viewModelOf(::MovieDetailsViewModel)
}