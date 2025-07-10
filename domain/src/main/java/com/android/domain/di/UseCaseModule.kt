package com.android.domain.di

import com.android.domain.usecase.GetLocalSuggestions
import org.koin.dsl.module

val useCaseModule = module{
    single { GetLocalSuggestions(get()) }
}