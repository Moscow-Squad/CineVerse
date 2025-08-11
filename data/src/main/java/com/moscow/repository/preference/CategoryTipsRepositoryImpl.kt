package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.moscow.domain.repository.CategoryTipsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryTipsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : CategoryTipsRepository {

    override suspend fun showCategoryDetailsTip(): Boolean {
        return dataStore.data.map { it[Keys.SHOW_COLLECTION_DETAILS_TIP] }.first() ?: true
    }

    override suspend fun closeCategoryDetailsTip() {
        dataStore.edit{ it[Keys.SHOW_COLLECTION_DETAILS_TIP] = false }
    }

    private object Keys {
        val SHOW_COLLECTION_DETAILS_TIP = booleanPreferencesKey("is_tip_collection_details")
    }
}
