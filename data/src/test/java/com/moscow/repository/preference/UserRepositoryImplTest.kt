package com.moscow.repository.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moscow.domain.repository.auth.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class UserRepositoryImplTest {

    private val dataStore = mockk<DataStore<Preferences>>()
    private lateinit var userRepositoryImpl: UserRepository

    @BeforeEach
    fun setup() {
        userRepositoryImpl = UserRepositoryImpl(dataStore)
    }


    @Test
    fun `getSessionId should return session id when preference exists`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = stringPreferencesKey("session_id")
        val expectedSessionId = "test_session_123"

        every { mockPreferences[key] } returns expectedSessionId
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.getSessionId()

        assertEquals(expectedSessionId, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `getSessionId should return empty string when preference is null`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = stringPreferencesKey("session_id")

        every { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.getSessionId()

        assertEquals("", result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `getSessionExpiration should return expiration date when preference exists`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = stringPreferencesKey("expired_at")
        val expectedExpiration = "2024-12-31T23:59:59Z"

        every { mockPreferences[key] } returns expectedExpiration
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.getSessionExpiration()

        assertEquals(expectedExpiration, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `getSessionExpiration should return empty string when preference is null`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = stringPreferencesKey("expired_at")

        every { mockPreferences[key] } returns null
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.getSessionExpiration()

        assertEquals("", result)
        coVerify(exactly = 1) { dataStore.data }
    }


    @Test
    fun `isGuest should return true when user type is guest`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = stringPreferencesKey("user_type")

        every { mockPreferences[key] } returns "guest"
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.isGuest()

        assertEquals(true, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `isGuest should return false when user type is not guest`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = stringPreferencesKey("user_type")

        every { mockPreferences[key] } returns "authenticated"
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.isGuest()

        assertEquals(false, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `isLoggedIn should return true when preference is true`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_logged_in")

        every { mockPreferences[key] } returns true
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.isLoggedIn()

        assertEquals(true, result)
        coVerify(exactly = 1) { dataStore.data }
    }

    @Test
    fun `isLoggedIn should return false when preference is false`() = runTest {
        val mockPreferences = mockk<Preferences>()
        val key = booleanPreferencesKey("is_logged_in")

        every { mockPreferences[key] } returns false
        coEvery { dataStore.data } returns flowOf(mockPreferences)

        val result = userRepositoryImpl.isLoggedIn()

        assertEquals(false, result)
        coVerify(exactly = 1) { dataStore.data }
    }
}