package com.moscow.cineverse.di

import com.di.dataModule
import com.di.repositoryModule

val appModule = dataModule + presentationModule  + repositoryModule