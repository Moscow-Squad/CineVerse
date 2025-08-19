package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.auth.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    @Named("user_settings") private val dataStore: DataStore<Preferences>
) : UserRepository {

    override suspend fun saveUser(userType: UserType) {
        dataStore.edit { preferences ->
            when (userType) {
                is UserType.AuthenticatedUser -> {
                    preferences[USER_TYPE_KEY] = AUTHENTICATED_USER
                    preferences[USER_ID_KEY] = userType.id
                    preferences[USERNAME_KEY] = userType.username
                    preferences[NAME_KEY] = userType.name
                    preferences[IMAGE_KEY] = userType.image.orEmpty()
                    preferences[SESSION_ID_KEY] = userType.sessionId
                    preferences[RECENTLY_VIEWED_COLLECTION_ID] = userType.recentlyCollectionId
                    preferences[Is_LOGGED_IN_KEY] = true
                    preferences[SHOW_COLLECTION_DETAILS_TIP] = true
                    preferences.remove(EXPIRED_AT_KEY)
                }

                is UserType.GuestUser -> {
                    preferences[USER_TYPE_KEY] = GUEST_USER
                    preferences[SESSION_ID_KEY] = userType.sessionId
                    preferences[EXPIRED_AT_KEY] = userType.expiredAt.toString()
                    preferences[Is_LOGGED_IN_KEY] = false
                    preferences[SHOW_COLLECTION_DETAILS_TIP] = true
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
                        val name = preferences[NAME_KEY] ?: ""
                        val image = preferences[IMAGE_KEY] ?: ""
                        val sessionId = preferences[SESSION_ID_KEY] ?: ""
                        val recentlyCollectionId = preferences[RECENTLY_VIEWED_COLLECTION_ID] ?: 0
                        UserType.AuthenticatedUser(
                            id = id,
                            username = username,
                            name = name,
                            sessionId = sessionId,
                            image = image,
                            recentlyCollectionId = recentlyCollectionId
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

    override suspend fun getSessionId(): String {
        return dataStore.data.map { it[SESSION_ID_KEY] }.firstOrNull() ?: ""
    }

    override suspend fun getSessionExpiration(): String {
        return dataStore.data.map { it[EXPIRED_AT_KEY] }.firstOrNull() ?: ""
    }

    override suspend fun clearUser() {
        dataStore.edit {
            it.clear()
            it[USER_TYPE_KEY] = GUEST_USER
            it[Is_LOGGED_IN_KEY] = false
        }
    }

    override suspend fun isGuest(): Boolean {
        return dataStore.data.map { it[USER_TYPE_KEY] == GUEST_USER }.firstOrNull() == true
    }

    override suspend fun isLoggedIn(): Boolean {
        return dataStore.data.map { it[Is_LOGGED_IN_KEY] }.first() == true
    }

    companion object {
        private const val AUTHENTICATED_USER = "authenticated"
        private const val GUEST_USER = "guest"
        private val USER_TYPE_KEY = stringPreferencesKey("user_type")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val NAME_KEY = stringPreferencesKey("name")
        private val IMAGE_KEY = stringPreferencesKey("image")
        private val SESSION_ID_KEY = stringPreferencesKey("session_id")
        private val RECENTLY_VIEWED_COLLECTION_ID = intPreferencesKey("recently_collection_id")
        private val EXPIRED_AT_KEY = stringPreferencesKey("expired_at")
        private val Is_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        private val SHOW_COLLECTION_DETAILS_TIP = booleanPreferencesKey("is_tip_collection_details")
    }
}