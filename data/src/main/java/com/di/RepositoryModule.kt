package com.di

import com.android.domain.repository.ActorDetailsRepository
import com.android.domain.repository.SearchRepository
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import com.android.domain.repository.ExploreRepository
import com.repository.actordetails.ActorDetailsRepositoryImpl
import com.repository.explore.ExploreRepositoryImpl
import com.repository.explore.search.SearchRepositoryImpl

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
    single<ActorDetailsRepository> {
        ActorDetailsRepositoryImpl(
            actorDetailsRemoteDataSource = get(),
            ioDispatcher = get(qualifier = named("IO")),
        )
    }
}