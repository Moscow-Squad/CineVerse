package com.moscow.cineverse

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.work.Configuration
import com.moscow.cineverse.main_activity.MainActivityViewModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class CineVerseApp : Application(), Configuration.Provider {
    @Inject lateinit var navigationDecider: NavigationDecider
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        precomputeNavigation()
    }

    private fun precomputeNavigation() {
        ProcessLifecycleOwner.get().lifecycleScope.launch(Default) {
            try {
                val destination = navigationDecider.determineDestination()

                MainActivityViewModel.setPrecomputedDestination(destination)
            } catch (e: Exception) {
                Log.e("CineVerseApp", "Failed to precompute navigation", e)
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
}