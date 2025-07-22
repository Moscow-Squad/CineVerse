package com.di

import com.android.domain.repository.ActorDetailsRepository
import com.android.domain.repository.CollectionsRepository
import com.android.domain.repository.DetailsRepository
import com.android.domain.repository.ExploreRepository
import com.android.domain.repository.RecommendationsMoviesRepository
import com.android.domain.repository.ReviewsRepository
import com.android.domain.repository.SearchRepository
import com.repository.ActorDetailsRepositoryImpl
import com.repository.CollectionsRepositoryImpl
import com.repository.DetailsRepositoryImpl
import com.repository.ExploreRepositoryImpl
import com.repository.SearchRepositoryImpl
import com.repository.RecommendationRepositoryImpl
import com.repository.ReviewsRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::ExploreRepositoryImpl) bind ExploreRepository::class
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::DetailsRepositoryImpl) bind DetailsRepository::class
    singleOf(::ReviewsRepositoryImpl) bind ReviewsRepository::class
    singleOf(::RecommendationRepositoryImpl) bind RecommendationsMoviesRepository::class
    singleOf(::ActorDetailsRepositoryImpl) bind ActorDetailsRepository::class
    singleOf(::CollectionsRepositoryImpl) bind CollectionsRepository::class

}