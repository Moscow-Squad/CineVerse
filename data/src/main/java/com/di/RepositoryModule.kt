package com.di

import com.android.domain.repository.ActorDetailsRepository
import com.android.domain.repository.CollectionsRepository
import com.android.domain.repository.DetailsRepository
import com.android.domain.repository.ExploreRepository
import com.android.domain.repository.RecommendationsMoviesRepository
import com.android.domain.repository.ReviewsRepository
import com.android.domain.repository.SearchRepository
import com.repository.actordetails.ActorDetailsRepositoryImpl
import com.repository.collections.CollectionsRepositoryImpl
import com.repository.details.DetailsRepositoryImpl
import com.repository.explore.ExploreRepositoryImpl
import com.repository.explore.search.SearchRepositoryImpl
import com.repository.recommendations.RecommendationsMoviesRepositoryImpl
import com.repository.reviews.ReviewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::ExploreRepositoryImpl) bind ExploreRepository::class
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::DetailsRepositoryImpl) bind DetailsRepository::class
    singleOf(::ReviewsRepositoryImpl) bind ReviewsRepository::class
    singleOf(::RecommendationsMoviesRepositoryImpl) bind RecommendationsMoviesRepository::class
    singleOf(::ActorDetailsRepositoryImpl) bind ActorDetailsRepository::class
    singleOf(::CollectionsRepositoryImpl) bind CollectionsRepository::class

}