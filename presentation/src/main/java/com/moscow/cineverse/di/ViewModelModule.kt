package com.moscow.cineverse.di

import com.moscow.cineverse.screen.castDetails.CastDetailsViewModel
import com.moscow.cineverse.screen.explore.ExploreViewModel
import com.moscow.cineverse.screen.cast_details_show_all.ShowAllActorMoviesInteractionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModels = module{
    viewModelOf(::ExploreViewModel)
    viewModelOf(::CastDetailsViewModel)
    viewModelOf(::ShowAllActorMoviesInteractionViewModel)
}