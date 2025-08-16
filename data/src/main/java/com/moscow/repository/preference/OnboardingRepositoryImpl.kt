package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.moscow.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : OnboardingRepository {

    override suspend fun isOnBoardingCompleted(): Boolean {
        return dataStore.data.map { it[Keys.IS_ON_BOARDING_SEEN] }.first() == true
    }

    override suspend fun setOnBoardingCompleted() {
        dataStore.edit { it[Keys.IS_ON_BOARDING_SEEN] = true }
    }

    private object Keys {
        val IS_ON_BOARDING_SEEN = booleanPreferencesKey("is_on_boarding_seen")
    }
}