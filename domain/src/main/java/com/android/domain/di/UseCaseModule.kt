package com.android.domain.di

import com.android.domain.usecase.actor.GetActorBestMoviesUseCase
import com.android.domain.usecase.actor.GetActorDetailsUseCase
import com.android.domain.usecase.actor.GetActorGalleryUseCase
import com.android.domain.usecase.collection.AddMediaItemToCollectionUseCase
import com.android.domain.usecase.collection.AddNewCollectionUseCase
import com.android.domain.usecase.collection.GetCollectionDetailsUseCase
import com.android.domain.usecase.collection.GetUserCollectionsUseCase
import com.android.domain.usecase.genre.GenreUseCase
import com.android.domain.usecase.login.LoginAsGuestUseCase
import com.android.domain.usecase.login.LoginWithUsernameAndPasswordUseCase
import com.android.domain.usecase.movie.GetMovieByGenreIdUseCase
import com.android.domain.usecase.movie.GetMovieCreditsUseCase
import com.android.domain.usecase.movie.GetMovieDetailsUseCase
import com.android.domain.usecase.movie.GetMovieRecommendationsUseCase
import com.android.domain.usecase.movie.GetPopularMoviesUseCase
import com.android.domain.usecase.movie.RateMovieUseCase
import com.android.domain.usecase.review.GetReviewsUseCase
import com.android.domain.usecase.search.CacheSearchQueryUseCase
import com.android.domain.usecase.search.ClearSearchHistoryUseCase
import com.android.domain.usecase.search.GetLocalSuggestionsUseCase
import com.android.domain.usecase.search.SearchUseCase
import com.android.domain.usecase.search.SuggestionUseCase
import com.android.domain.usecase.series.GetLatestSeasonsUseCase
import com.android.domain.usecase.series.GetListOfSeriesUseCase
import com.android.domain.usecase.series.GetPopularSeriesUseCase
import com.android.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.series.GetSeriesDetailUseCase
import com.android.domain.usecase.series.RateSeriesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.android.domain.usecase.series.GetSeriesRecommendationsUseCase
import com.android.domain.usecase.series.GetSeriesCreditsDetailsUseCase

val useCaseModule = module {
    singleOf(::GetLocalSuggestionsUseCase)
    singleOf(::SuggestionUseCase)
    singleOf(::SearchUseCase)
    singleOf(::GenreUseCase)
    singleOf(::GetPopularMoviesUseCase)
    singleOf(::GetPopularSeriesUseCase)
    singleOf(::GetMovieByGenreIdUseCase)
    singleOf(::GetSeriesByGenreIdUseCase)
    singleOf(::GetActorDetailsUseCase)
    singleOf(::GetActorGalleryUseCase)
    singleOf(::GetActorBestMoviesUseCase)
    singleOf(::GetMovieDetailsUseCase)
    singleOf(::GetSeriesDetailUseCase)
    singleOf(::GetReviewsUseCase)
    singleOf(::GetMovieCreditsUseCase)
    singleOf(::GetMovieRecommendationsUseCase)
    singleOf(::GetLatestSeasonsUseCase)
    singleOf(::GetListOfSeriesUseCase)
    singleOf(::CacheSearchQueryUseCase)
    singleOf(::ClearSearchHistoryUseCase)
    singleOf(::GetUserCollectionsUseCase)
    singleOf(::AddNewCollectionUseCase)
    singleOf(::AddMediaItemToCollectionUseCase)
    singleOf(::GetCollectionDetailsUseCase)
    singleOf(::RateMovieUseCase)
    singleOf(::RateSeriesUseCase)
    singleOf(::LoginWithUsernameAndPasswordUseCase)
    singleOf(::LoginAsGuestUseCase)
    singleOf(::GetSeriesCreditsDetailsUseCase)
    singleOf(::GetSeriesRecommendationsUseCase)}