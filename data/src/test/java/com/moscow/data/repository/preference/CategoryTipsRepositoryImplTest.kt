package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.moscow.domain.repository.CategoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CategoryTipsRepositoryImplTest {

    private val dataStore = mockk<DataStore<Preferences>>()
    private lateinit var repository: CategoryTipsRepository

    @BeforeEach
    fun setup() {
        repository = CategoryTipsRepositoryImpl(dataStore)
    }

    @Test
    fun `showCategoryDetailsTip returns true when preference is not set`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_tip_collection_details")

        coEvery { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        try {
            val result = repository.showCategoryDetailsTip()
            assertEquals(true, result)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `showCategoryDetailsTip returns true when preference is true`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_tip_collection_details")

        coEvery { mockPreferences[key] } returns true
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        try {
            val result = repository.showCategoryDetailsTip()
            assertEquals(true, result)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `showCategoryDetailsTip returns false when preference is false`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_tip_collection_details")

        coEvery { mockPreferences[key] } returns false
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        try {
            val result = repository.showCategoryDetailsTip()
            assertEquals(false, result)
        } catch (e: Exception) {
            assertEquals("Expected successful method call", e.message ?: "Unexpected error")
        }

        coVerify(exactly = 1) { dataStore.data }
    }

}