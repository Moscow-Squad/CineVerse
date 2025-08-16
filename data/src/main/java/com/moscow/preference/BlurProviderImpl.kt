package com.moscow.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.domain.service.blur.BlurProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class BlurProviderImpl @Inject constructor(
    @Named("user_settings") private val dataStore: DataStore<Preferences>
) : BlurProvider {

    private val _blurState = MutableStateFlow(DEFAULT_BLUR_ENABLED)
    override val blurFlow: StateFlow<String> = _blurState

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.data
                .map { it[BLUR_ENABLED_KEY] ?: DEFAULT_BLUR_ENABLED }
                .distinctUntilChanged()
                .collect { _blurState.value = it }
        }
    }

    override suspend fun changeBlur(enabled: String) {
        dataStore.edit { it[BLUR_ENABLED_KEY] = enabled }
    }

    override suspend fun clearBlur() {
        dataStore.edit { it.remove(BLUR_ENABLED_KEY) }
    }

    companion object {
        private val BLUR_ENABLED_KEY = stringPreferencesKey("blur_threshold")
        private const val DEFAULT_BLUR_ENABLED = "high"
    }
}
