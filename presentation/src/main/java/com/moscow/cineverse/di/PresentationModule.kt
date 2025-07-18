package com.moscow.cineverse.di

import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.GetMovieByGenreIdUseCase
import com.android.domain.usecase.GetMovieDetailUseCase
import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.GetSeriesDetailUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.android.domain.usecase.SearchUseCase
import com.android.domain.usecase.SuggestionUseCase
import com.android.domain.usecase.actordetails.GetActorBestOfMovies
import com.android.domain.usecase.actordetails.GetActorDetails
import com.android.domain.usecase.actordetails.GetActorGallery
import com.android.domain.usecase.login.LoginAsGuestUseCase
import com.android.domain.usecase.login.LoginWithUsernameAndPasswordUseCase
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
    single { GetActorDetails(get()) }
    single { GetActorGallery(get()) }
    single { GetActorBestOfMovies(get()) }
    single { GetMovieDetailUseCase(get()) }
    single { GetSeriesDetailUseCase(get()) }
    single { GetReviewsPageUseCase(get()) }
    single { LoginWithUsernameAndPasswordUseCase(get()) }
    single { LoginAsGuestUseCase(get()) }
}

val presentationModule = viewModels + useCases
