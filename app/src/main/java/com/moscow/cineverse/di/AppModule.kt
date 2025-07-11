package com.moscow.cineverse.di

import com.di.dataModule
import com.di.repositoryModule
import com.di.workManagerModule

val appModule = dataModule + presentationModule + workManagerModule + repositoryModule