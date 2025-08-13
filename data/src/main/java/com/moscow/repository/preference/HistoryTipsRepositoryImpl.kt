package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.moscow.domain.repository.HistoryTipsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryTipsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : HistoryTipsRepository {

    override suspend fun showHistoryTip(): Boolean {
        return dataStore.data.map { it[SHOW_HISTORY_TIP] }.first() != false
    }

    override suspend fun closeHistoryTip() {
        dataStore.edit { preferences ->
            preferences[SHOW_HISTORY_TIP] = false
        }
    }

    private companion object Keys {
        private val SHOW_HISTORY_TIP = booleanPreferencesKey("is_tip_history")
    }
}