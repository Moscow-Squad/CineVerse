package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.google.common.truth.Truth.assertThat
import com.moscow.domain.repository.HistoryTipsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HistoryTipsRepositoryImplTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: HistoryTipsRepository

    @BeforeEach
    fun setup() {
        dataStore = mockk()
        repository = HistoryTipsRepositoryImpl(dataStore)
    }

    @Test
    fun `showHistoryTip returns true when preference is null`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_tip_history")

        coEvery { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = repository.showHistoryTip()

        assertThat(result).isTrue()
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `showHistoryTip returns true when preference is true`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_tip_history")

        coEvery { mockPreferences[key] } returns true
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = repository.showHistoryTip()

        assertThat(result).isTrue()
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `showHistoryTip returns false when preference is false`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_tip_history")

        coEvery { mockPreferences[key] } returns false
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = repository.showHistoryTip()

        assertThat(result).isFalse()
        coVerify(exactly = 1) { dataStore.data }
    }

}