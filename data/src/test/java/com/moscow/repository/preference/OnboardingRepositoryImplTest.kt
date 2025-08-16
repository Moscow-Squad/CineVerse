package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class OnboardingRepositoryImplTest {

    private val dataStore = mockk<DataStore<Preferences>>()
    private lateinit var onboardingRepositoryImpl: OnboardingRepositoryImpl

    @BeforeEach
    fun setup() {
        onboardingRepositoryImpl = OnboardingRepositoryImpl(dataStore)
    }

    @Test
    fun `isOnBoardingCompleted should return true when preference is true`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_on_boarding_seen")

        every { mockPreferences[key] } returns true
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = onboardingRepositoryImpl.isOnBoardingCompleted()

        assertEquals(true, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `isOnBoardingCompleted should return false when preference is false`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_on_boarding_seen")

        every { mockPreferences[key] } returns false
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = onboardingRepositoryImpl.isOnBoardingCompleted()

        assertEquals(false, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `isOnBoardingCompleted should return false when preference is null`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_on_boarding_seen")

        every { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = onboardingRepositoryImpl.isOnBoardingCompleted()

        assertEquals(false, result)
        coVerify(exactly = 1) { dataStore.data }
    }

}