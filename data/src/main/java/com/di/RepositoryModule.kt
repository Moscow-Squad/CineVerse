package com.di

import com.android.domain.repository.ActorDetailsRepository
import com.android.domain.repository.DetailsRepository
import com.android.domain.repository.ExploreRepository
import com.android.domain.repository.RecommendationsMoviesRepository
import com.android.domain.repository.ReviewsRepository
import com.android.domain.repository.SearchRepository
import com.repository.actordetails.ActorDetailsRepositoryImpl
import com.repository.details.DetailsRepositoryImpl
import com.repository.explore.ExploreRepositoryImpl
import com.repository.explore.search.SearchRepositoryImpl
import com.repository.recommendations.RecommendationsMoviesRepositoryImpl
import com.repository.reviews.ReviewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<ExploreRepository> {
        ExploreRepositoryImpl(
            ioDispatcher = get(qualifier = named("IO")),
            exploreRemoteDataSource = get()
        )
    }
    single(named("IO")) { Dispatchers.IO }

    single<SearchRepository> {
        SearchRepositoryImpl(
            searchRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO")),
            searchLocalDateSource = get(),
            workManager = get()
        )
    }
    single<DetailsRepository> {
        DetailsRepositoryImpl(
            get()
        )
    }

    single<ReviewsRepository> {
        ReviewsRepositoryImpl(
            reviewsRemoteDataSource =  get() ,
        )
    }
    single<RecommendationsMoviesRepository> {
        RecommendationsMoviesRepositoryImpl(
            get() ,
        )
    }

    single<ActorDetailsRepository> {
        ActorDetailsRepositoryImpl(
            actorDetailsRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO")),
        )
    }
}