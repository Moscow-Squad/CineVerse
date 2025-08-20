package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RatingTipsRepositoryImplTest {

    private val dataStore = mockk<DataStore<Preferences>>()
    private lateinit var ratingTipsRepositoryImpl: RatingTipsRepositoryImpl

    private val key = booleanPreferencesKey("is_rating_history")

    @BeforeEach
    fun setup() {
        ratingTipsRepositoryImpl = RatingTipsRepositoryImpl(dataStore)
    }

    @Test
    fun `showRatingTip should return true when preference is true`() = runTest {
        val mockPreferences = mockk<Preferences>()
        every { mockPreferences[key] } returns true
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = ratingTipsRepositoryImpl.showRatingTip()

        assertThat(result).isTrue()
    }

    @Test
    fun `showRatingTip should return true when preference is null`() = runTest {
        val mockPreferences = mockk<Preferences>()
        every { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = ratingTipsRepositoryImpl.showRatingTip()

        assertThat(result).isTrue()
    }

    @Test
    fun `showRatingTip should return false when preference is false`() = runTest {
        val mockPreferences = mockk<Preferences>()
        every { mockPreferences[key] } returns false
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = ratingTipsRepositoryImpl.showRatingTip()

        assertThat(result).isFalse()
    }
}