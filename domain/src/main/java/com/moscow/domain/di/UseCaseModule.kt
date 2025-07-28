package com.moscow.domain.di

import com.moscow.domain.usecase.actor.GetActorBestMoviesUseCase
import com.moscow.domain.usecase.actor.GetActorDetailsUseCase
import com.moscow.domain.usecase.actor.GetActorGalleryUseCase
import com.moscow.domain.usecase.collection.AddMediaItemToCollectionUseCase
import com.moscow.domain.usecase.collection.AddNewCollectionUseCase
import com.moscow.domain.usecase.collection.GetCollectionDetailsUseCase
import com.moscow.domain.usecase.collection.GetUserCollectionsUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.home.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.home.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.home.GetTopRatedTVShowsUseCase
import com.moscow.domain.usecase.home.GetTrendingMoviesUseCase
import com.moscow.domain.usecase.home.GetUpcomingMoviesUseCase
import com.moscow.domain.usecase.login.LoginAsGuestUseCase
import com.moscow.domain.usecase.login.LoginWithUsernameAndPasswordUseCase
import com.moscow.domain.usecase.movie.GetMovieByGenreIdUseCase
import com.moscow.domain.usecase.movie.GetMovieCreditsUseCase
import com.moscow.domain.usecase.movie.GetMovieDetailsUseCase
import com.moscow.domain.usecase.movie.GetMovieRecommendationsUseCase
import com.moscow.domain.usecase.movie.GetPopularMoviesUseCase
import com.moscow.domain.usecase.movie.RateMovieUseCase
import com.moscow.domain.usecase.review.GetReviewsUseCase
import com.moscow.domain.usecase.search.CacheSearchQueryUseCase
import com.moscow.domain.usecase.search.ClearSearchHistoryUseCase
import com.moscow.domain.usecase.search.GetLocalSuggestionsUseCase
import com.moscow.domain.usecase.search.SearchUseCase
import com.moscow.domain.usecase.search.SuggestionUseCase
import com.moscow.domain.usecase.series.GetLatestSeasonsUseCase
import com.moscow.domain.usecase.series.GetListOfSeriesUseCase
import com.moscow.domain.usecase.series.GetPopularSeriesUseCase
import com.moscow.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.moscow.domain.usecase.series.GetSeriesCreditsDetailsUseCase
import com.moscow.domain.usecase.series.GetSeriesDetailUseCase
import com.moscow.domain.usecase.series.GetSeriesRecommendationsUseCase
import com.moscow.domain.usecase.series.RateSeriesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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
    singleOf(::GetSeriesCreditsDetailsUseCase)
    singleOf(::GetSeriesRecommendationsUseCase)
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
    singleOf(::GetTrendingMoviesUseCase)
    singleOf(::GetMatchesYourVibesMoviesUseCase)
    singleOf(::GetRecentlyReleasedMoviesUseCase)
    singleOf(::GetTopRatedTVShowsUseCase)
    singleOf(::GetUpcomingMoviesUseCase)
}