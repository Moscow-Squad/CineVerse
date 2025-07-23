package com.moscow.cineverse.di

import com.android.domain.di.useCaseModule
import com.di.dataModule
import com.di.repositoryModule
import com.di.workManagerModule
import kotlin.collections.plus

val appModule = dataModule + workManagerModule + repositoryModule + useCaseModule + viewModelModule