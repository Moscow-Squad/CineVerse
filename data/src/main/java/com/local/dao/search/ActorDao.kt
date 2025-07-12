package com.local.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.local.entity.ActorEntity

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actor: List<ActorEntity>)

    @Query("SELECT * FROM actor_table WHERE searchTerm = :searchTerm")
    suspend fun getActorsBySearchTerm(searchTerm: String): List<ActorEntity>
}