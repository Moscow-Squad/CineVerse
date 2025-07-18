package com.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.android.domain.model.UserType
import com.android.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class PreferenceRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): PreferenceRepository{

    override suspend fun saveUser(userType: UserType) {
        dataStore.edit {preferences->
            when(userType){
                is UserType.AuthenticatedUser -> {
                    preferences[USER_TYPE_KEY] = AUTHENTICATED_USER
                    preferences[USER_ID_KEY] = userType.id
                    preferences[USERNAME_KEY] = userType.username
                    preferences[SESSION_ID_KEY] = userType.sessionId
                    preferences[ARE_LOGGED_IN_KEY] = true
                    preferences.remove(EXPIRED_AT_KEY)
                }
                is UserType.GuestUser -> {
                    preferences[USER_TYPE_KEY] = GUEST_USER
                    preferences[SESSION_ID_KEY] = userType.sessionId
                    preferences[EXPIRED_AT_KEY] = userType.expiredAt.toString()
                    preferences.remove(USER_ID_KEY)
                    preferences.remove(USERNAME_KEY)
                }
            }
        }
    }

    override suspend fun getUser(): UserType {
        return dataStore.data
            .map { preferences ->
                when (preferences[USER_TYPE_KEY]) {
                    AUTHENTICATED_USER -> {
                        val id = preferences[USER_ID_KEY] ?: ""
                        val username = preferences[USERNAME_KEY] ?: ""
                        val sessionId = preferences[SESSION_ID_KEY] ?: ""
                        UserType.AuthenticatedUser(
                            id = id,
                            username = username,
                            sessionId = sessionId
                        )
                    }

                    GUEST_USER -> {
                        val sessionId = preferences[SESSION_ID_KEY] ?: ""
                        val expiredAt = preferences[EXPIRED_AT_KEY] ?: ""
                        UserType.GuestUser(
                            sessionId = sessionId,
                            expiredAt = expiredAt
                        )
                    }
                    else -> throw IllegalStateException("Invalid user type")
                }
            }
            .first()
    }

    override suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }

    override suspend fun isGuest(): Boolean {
        return dataStore.data.map { it[USER_TYPE_KEY] == GUEST_USER }.firstOrNull() == true
    }

    override suspend fun areLoggedIn(): Boolean {
        return dataStore.data.map { it[ARE_LOGGED_IN_KEY] }.firstOrNull() == true
    }

    companion object {
        private const val AUTHENTICATED_USER = "authenticated"
        private const val GUEST_USER = "guest"
        private val USER_TYPE_KEY = stringPreferencesKey("user_type")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val SESSION_ID_KEY = stringPreferencesKey("session_id")
        private val EXPIRED_AT_KEY = stringPreferencesKey("expired_at")
        private val ARE_LOGGED_IN_KEY = booleanPreferencesKey("are_logged_in")
    }
}