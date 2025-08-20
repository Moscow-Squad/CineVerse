package com.moscow.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.MutableStateFlow

class FakeDataStore(initial: Preferences = emptyPreferences()) :
    DataStore<Preferences> {
    private val state = MutableStateFlow(initial)

    override val data = state

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
        val updated = transform(state.value)
        state.value = updated
        return updated
    }
}