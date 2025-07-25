package com.moscow.local.dao.search

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.moscow.local.database.CineVerseDataBase
import com.moscow.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchHistoryDaoTest {

    private lateinit var db: CineVerseDataBase
    private lateinit var dao: SearchHistoryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CineVerseDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = db.searchHistoryDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertSearchHistory_shouldInsertItem() = runTest {
        val history = SearchHistoryEntity(searchTerm = "nour")
        dao.insertSearchHistory(history)

        val result = dao.getAllSearchHistory().first()
        assertThat(result).containsExactly("nour")
    }

    @Test
    fun insertSearchHistory_shouldReplaceOnConflict() = runTest {
        val original = SearchHistoryEntity(searchTerm = "nour")
        val updated = SearchHistoryEntity(searchTerm = "nour")

        dao.insertSearchHistory(original)
        dao.insertSearchHistory(updated)

        val result = dao.getAllSearchHistory().first()
        assertThat(result).containsExactly("nour")
    }

    @Test
    fun deleteSearchHistory_shouldRemoveItem() = runTest {
        val history = SearchHistoryEntity(searchTerm = "nour")
        dao.insertSearchHistory(history)

        dao.deleteSearchHistory(history)
        val result = dao.getAllSearchHistory().first()
        assertThat(result).doesNotContain("nour")
    }

    @Test
    fun deleteAllSearchHistory_shouldClearTable() = runTest {
        dao.insertSearchHistory(SearchHistoryEntity(searchTerm = "nour"))
        dao.insertSearchHistory(SearchHistoryEntity(searchTerm = "nour"))

        dao.deleteAllSearchHistory()
        val result = dao.getAllSearchHistory().first()
        assertThat(result).isEmpty()
    }
}
