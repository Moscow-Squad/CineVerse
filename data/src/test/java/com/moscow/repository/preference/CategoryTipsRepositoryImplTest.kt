package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.moscow.domain.repository.CategoryTipsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CategoryTipsRepositoryImplTest {

    private val dataStore = mockk<DataStore<Preferences>>()
    private lateinit var repository: CategoryTipsRepository

    private val key = booleanPreferencesKey("is_tip_collection_details")

    @BeforeEach
    fun setup() {
        repository = CategoryTipsRepositoryImpl(dataStore)
    }

    @Test
    fun `showCategoryDetailsTip returns true when preference is not set`() = runTest {
        val mockPreferences = mockk<Preferences>()
        every { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = repository.showCategoryDetailsTip()

        assertThat(result).isTrue()
    }

    @Test
    fun `showCategoryDetailsTip returns true when preference is true`() = runTest {
        val mockPreferences = mockk<Preferences>()
        every { mockPreferences[key] } returns true
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = repository.showCategoryDetailsTip()

        assertThat(result).isTrue()
    }

    @Test
    fun `showCategoryDetailsTip returns false when preference is false`() = runTest {
        val mockPreferences = mockk<Preferences>()
        every { mockPreferences[key] } returns false
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = repository.showCategoryDetailsTip()

        assertThat(result).isFalse()
    }
}