package com.moscow.cineverse.di

import com.moscow.cineverse.screen.castDetails.CastDetailsViewModel
import com.moscow.cineverse.screen.explore.ExploreViewModel
import com.moscow.cineverse.screen.castDetails.best0fmovies.ShowAllActorMoviesInteractionViewModel
import com.moscow.cineverse.screen.castDetails.gallery.ActorGalleryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModels = module{
    viewModelOf(::ExploreViewModel)
    viewModel { (actorId: Int) ->
        CastDetailsViewModel(
            getActorBestOfMovies = get(),
            getActorDetails = get(),
            getActorGallery = get(),
            actorId = actorId,
        )
    }
    viewModel { (actorId: Int, actorName: String) ->
        ShowAllActorMoviesInteractionViewModel(
            getActorBestOfMovies = get(),
            genreUseCase = get(),
            actorId   = actorId,
        )
    }
    viewModel { (actorId: Int, actorName: String) ->
        ActorGalleryViewModel(
            getActorGallery = get(),
            actorId   = actorId,
            actorName = actorName,
        )
    }
}