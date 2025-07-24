package com.moscow.di

import org.koin.dsl.module

val dataModule = module{
    includes(
        dataSourceModule,
        localSourceModule,
    )
}