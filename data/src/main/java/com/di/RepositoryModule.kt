package com.di

import com.android.domain.repository.ActorDetailsRepository
import com.android.domain.repository.CollectionsRepository
import com.android.domain.repository.DetailsRepository
import com.android.domain.repository.ExploreRepository
import com.android.domain.repository.RecommendationsMoviesRepository
import com.android.domain.repository.ReviewsRepository
import com.android.domain.repository.LoginRepository
import com.android.domain.repository.PreferenceRepository
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
import com.repository.actordetails.ActorDetailsRepositoryImpl
import com.repository.collections.CollectionsRepositoryImpl
import com.repository.details.DetailsRepositoryImpl
import com.repository.explore.ExploreRepositoryImpl
import com.repository.explore.search.SearchRepositoryImpl
import com.repository.recommendations.RecommendationsMoviesRepositoryImpl
import com.repository.reviews.ReviewsRepositoryImpl
import com.repository.login.LoginRepositoryImpl
import com.repository.preference.PreferenceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ExploreRepositoryImpl) bind ExploreRepository::class
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::DetailsRepositoryImpl) bind DetailsRepository::class
    singleOf(::ReviewsRepositoryImpl) bind ReviewsRepository::class
    singleOf(::RecommendationRepositoryImpl) bind RecommendationsMoviesRepository::class
    singleOf(::ActorDetailsRepositoryImpl) bind ActorDetailsRepository::class
    singleOf(::CollectionsRepositoryImpl) bind CollectionsRepository::class
    single<LoginRepository> {
        LoginRepositoryImpl(
            loginRemoteDataSource = get(),
            preferenceRepository = get()
        )
    }
    single<PreferenceRepository> {
        PreferenceRepositoryImpl(get())
    }
}