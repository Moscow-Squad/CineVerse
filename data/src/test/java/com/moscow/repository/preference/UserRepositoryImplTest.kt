package com.moscow.repository.preference

import com.moscow.domain.model.UserType
import com.moscow.helper.FakeDataStore
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRepositoryImplTest {

    private lateinit var dataStore: FakeDataStore
    private lateinit var repository: UserRepositoryImpl

    @BeforeEach
    fun setup() {
        dataStore = FakeDataStore()
        repository = UserRepositoryImpl(dataStore)
    }

    @Test
    fun `saveUser stores authenticated user correctly`() = runTest {
        val user = UserType.AuthenticatedUser(
            id = "123",
            username = "ahmed",
            name = "Ahmed",
            sessionId = "sid_abc",
            image = "image.jpg",
            recentlyCollectionId = 42
        )

        repository.saveUser(user)

        val result = repository.getUser()
        assertTrue(result is UserType.AuthenticatedUser)
        with(result as UserType.AuthenticatedUser) {
            assertEquals("123", id)
            assertEquals("ahmed", username)
            assertEquals("Ahmed", name)
            assertEquals("sid_abc", sessionId)
            assertEquals("image.jpg", image)
            assertEquals(42, recentlyCollectionId)
        }
    }

    @Test
    fun `saveUser stores guest user correctly`() = runTest {
        val user = UserType.GuestUser(
            sessionId = "sid_guest",
            expiredAt = "2025-12-31T23:59:59Z"
        )

        repository.saveUser(user)

        val result = repository.getUser()
        assertTrue(result is UserType.GuestUser)
        with(result as UserType.GuestUser) {
            assertEquals("sid_guest", sessionId)
            assertEquals("2025-12-31T23:59:59Z", expiredAt)
        }
    }

    @Test
    fun `getSessionId returns stored session id`() = runTest {
        val user = UserType.AuthenticatedUser(
            id = "123", username = "ahmed", name = "Ahmed",
            sessionId = "sid_abc", image = "", recentlyCollectionId = 0
        )
        repository.saveUser(user)

        val sessionId = repository.getSessionId()
        assertEquals("sid_abc", sessionId)
    }

    @Test
    fun `getSessionExpiration returns stored expiration`() = runTest {
        val user = UserType.GuestUser("sid_guest", "2025-12-31T23:59:59Z")
        repository.saveUser(user)

        val expiration = repository.getSessionExpiration()
        assertEquals("2025-12-31T23:59:59Z", expiration)
    }

    @Test
    fun `clearUser removes all preferences`() = runTest {
        val user = UserType.AuthenticatedUser(
            id = "123", username = "ahmed", name = "Ahmed",
            sessionId = "sid_abc", image = "", recentlyCollectionId = 0
        )
        repository.saveUser(user)

        repository.clearUser()

        val sessionId = repository.getSessionId()
        assertEquals("", sessionId)
    }

    @Test
    fun `isGuest returns true for guest user`() = runTest {
        val user = UserType.GuestUser("sid_guest", "2025-12-31T23:59:59Z")
        repository.saveUser(user)

        val result = repository.isGuest()
        assertTrue(result)
    }

    @Test
    fun `isLoggedIn returns true for authenticated user`() = runTest {
        val user = UserType.AuthenticatedUser(
            id = "123", username = "ahmed", name = "Ahmed",
            sessionId = "sid_abc", image = "", recentlyCollectionId = 0
        )
        repository.saveUser(user)

        val result = repository.isLoggedIn()
        assertTrue(result)
    }
}