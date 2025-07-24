package com.moscow.cineverse.di

import com.moscow.cineverse.navigation.NavViewModel
import com.moscow.cineverse.screen.castDetails.CastDetailsViewModel
import com.moscow.cineverse.screen.castDetails.best0fmovies.ShowAllActorMoviesViewModel
import com.moscow.cineverse.screen.castDetails.gallery.ActorGalleryViewModel
import com.moscow.cineverse.screen.collections.CollectionsBottomSheetViewModel
import com.moscow.cineverse.screen.explore.ExploreViewModel
import com.moscow.cineverse.screen.movie_details.MovieDetailsViewModel
import com.moscow.cineverse.screen.movie_details.recommendations.RecommendationsMoviesViewModel
import com.moscow.cineverse.screen.reviews.ReviewsViewModel
import com.moscow.cineverse.screen.series_details.SeriesDetailsScreenScreenViewModel
import com.moscow.cineverse.screen.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ExploreViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SeriesDetailsScreenScreenViewModel)
    viewModelOf(::CollectionsBottomSheetViewModel)
    viewModelOf(::ReviewsViewModel)
    viewModelOf(::RecommendationsMoviesViewModel)
    viewModel {
        LoginViewModel(
            context = androidContext(),
            loginWithUsernameAndPasswordUseCase = get(),
            loginAsGuestUseCase = get()
        )
    }
    viewModel { (actorId: Int) ->
        CastDetailsViewModel(
            getActorBestMoviesUseCase = get(),
            getActorDetailsUseCase = get(),
            getActorGalleryUseCase = get(),
            actorId = actorId,
        )
    }
    viewModel { (actorId: Int, actorName: String) ->
        ShowAllActorMoviesViewModel(
            getActorBestMoviesUseCase = get(),
            genreUseCase = get(),
            actorId = actorId,
        )
    }
    viewModel { (actorId: Int, actorName: String) ->
        ActorGalleryViewModel(
            getActorGalleryUseCase = get(),
            actorId = actorId,
            actorName = actorName,
        )
    }
    viewModel {
        NavViewModel(
            preferenceRepository = get()
        )
    }
}