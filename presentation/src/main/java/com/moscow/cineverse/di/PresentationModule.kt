package com.moscow.cineverse.di

import com.android.domain.usecase.AddMediaItemToCollectionUseCase
import com.android.domain.usecase.AddNewCollectionUseCase
import com.android.domain.usecase.CacheSearchQueryUseCase
import com.android.domain.usecase.ClearSearchHistoryUseCase
import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetCollectionDetailsUseCase
import com.android.domain.usecase.GetCreditsUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.GetMovieByGenreIdUseCase
import com.android.domain.usecase.GetMovieDetailUseCase
import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetRecommendationsUseCase
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.android.domain.usecase.GetUserCollectionsUseCase
import com.android.domain.usecase.RateMovieUseCase
import com.android.domain.usecase.RateSeriesUseCase
import com.android.domain.usecase.SearchUseCase
import com.android.domain.usecase.SuggestionUseCase
import com.android.domain.usecase.actordetails.GetActorBestOfMovies
import com.android.domain.usecase.actordetails.GetActorDetails
import com.android.domain.usecase.actordetails.GetActorGallery
import com.android.domain.usecase.seriesdetails.GetLatestSeasonsUseCase
import com.android.domain.usecase.seriesdetails.GetListOfSeriesUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesCreditsDetailsUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesDetailUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesRecommendationsUseCase

import org.koin.core.module.dsl.singleOf

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
    single { GetCreditsUseCase(get()) }
    single { GetRecommendationsUseCase(get()) }
    single { GetLatestSeasonsUseCase(get()) }
    single { GetListOfSeriesUseCase(get()) }
    singleOf(::CacheSearchQueryUseCase)
    singleOf(::ClearSearchHistoryUseCase)
    singleOf(::GetSeriesCreditsDetailsUseCase)
    singleOf(::GetSeriesRecommendationsUseCase)

    single { GetUserCollectionsUseCase(get()) }
    single { AddNewCollectionUseCase(get()) }
    single { AddMediaItemToCollectionUseCase(get()) }
    single { GetCollectionDetailsUseCase(get()) }
    single { RateMovieUseCase(get()) }
    single { RateSeriesUseCase(get()) }
}

val presentationModule = viewModels + useCases
