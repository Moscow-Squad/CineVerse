package com.moscow.local.dao.search

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.moscow.local.database.CineVerseDataBase
import com.moscow.local.entity.ActorEntity
import com.moscow.local.entity.Gender
import com.moscow.local.entity.SearchHistoryEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActorDaoTest {

    private lateinit var db: CineVerseDataBase
    private lateinit var actorDao: ActorDao
    private lateinit var searchHistoryDao: SearchHistoryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CineVerseDataBase::class.java
        ).allowMainThreadQueries().build()

        actorDao = db.actorDao()
        searchHistoryDao = db.searchHistoryDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertActors_shouldInsertCorrectly() = runTest {
        val actors = listOf(
            ActorEntity(
                id = 1,
                searchTerm = "nour",
                name = "actor1",
                gender = Gender.FEMALE,
                profileImg = ""
            ),
            ActorEntity(
                id = 2,
                searchTerm = "nour",
                name = "actor1",
                gender = Gender.FEMALE,
                profileImg = ""
            )
        )

        searchHistoryDao.insertSearchHistory(SearchHistoryEntity("nour"))
        actorDao.insertActors(actors)
        val result = actorDao.getActorsBySearchTerm("nour")

        assertThat(result).isEqualTo(actors)
    }
}
