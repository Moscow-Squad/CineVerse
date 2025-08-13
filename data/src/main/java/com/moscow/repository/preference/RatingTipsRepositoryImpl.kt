package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.moscow.domain.repository.RatingTipsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RatingTipsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : RatingTipsRepository {

    override suspend fun showRatingTip(): Boolean {
        return dataStore.data.map { it[SHOW_RATING_TIP] }.first() != false
    }

    override suspend fun closeRatingTip() {
        dataStore.edit { preferences ->
            preferences[SHOW_RATING_TIP] = false
        }
    }

    private companion object Keys {
        private val SHOW_RATING_TIP = booleanPreferencesKey("is_rating_history")
    }
}