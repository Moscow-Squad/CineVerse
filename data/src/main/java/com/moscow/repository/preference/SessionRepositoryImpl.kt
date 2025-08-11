package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.domain.repository.SessionRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SessionRepository {

    override suspend fun getSessionId(): String {
        return dataStore.data.map { it[Keys.SESSION_ID] }.firstOrNull() ?: ""
    }

    override suspend fun getSessionExpiration(): String {
        return dataStore.data.map { it[Keys.EXPIRED_AT] }.firstOrNull() ?: ""
    }

    private object Keys {
        val SESSION_ID = stringPreferencesKey("session_id")
        val EXPIRED_AT = stringPreferencesKey("expired_at")
    }
}