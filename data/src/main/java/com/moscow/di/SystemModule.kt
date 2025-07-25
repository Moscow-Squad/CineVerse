package com.moscow.di

import com.moscow.DeviceLanguageProvider
import com.moscow.data_source.system.LanguageProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val systemModule = module {
    single<LanguageProvider> { DeviceLanguageProvider() }
}