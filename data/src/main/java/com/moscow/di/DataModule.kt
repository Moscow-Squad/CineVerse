package com.moscow.di

import com.di.remoteDataSourceModule
import org.koin.dsl.module

val dataModule = module{
    includes(
        systemModule,
        remoteDataSourceModule,
        localSourceModule,
    )
}