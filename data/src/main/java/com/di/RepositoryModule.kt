package com.di

import com.android.domain.SearchRepository
import com.repository.search.SearchRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
}