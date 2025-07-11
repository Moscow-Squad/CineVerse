package com.moscow.cineverse.di

import com.android.domain.di.domainModule
import com.di.dataModule

val appModule = dataModule + domainModule + presentationModule